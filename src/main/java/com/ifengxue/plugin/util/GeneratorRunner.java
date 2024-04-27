package com.ifengxue.plugin.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;
 
import org.apache.commons.lang3.StringUtils; 
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.db.DatabaseIntrospector;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
 
import com.ifengxue.plugin.entity.Column;
import com.ifengxue.plugin.entity.ColumnSchema;
import com.ifengxue.plugin.entity.Table;
import com.ifengxue.plugin.entity.TableSchema;
import com.ifengxue.plugin.entity.MybatisGeneratorTableSchema;
import com.ifengxue.plugin.generator.config.DriverConfig;
import com.ifengxue.plugin.generator.config.GeneratorConfig;
import com.ifengxue.plugin.generator.config.TablesConfig;
import com.ifengxue.plugin.generator.config.TablesConfig.ORM;
import com.ifengxue.plugin.generator.source.EntitySourceParserV2;
import com.ifengxue.plugin.generator.source.SourceParser;
import com.ifengxue.plugin.generator.source.VelocityEngineAware;
import com.ifengxue.plugin.state.AutoGeneratorSettingsState;
import com.ifengxue.plugin.state.AutoGeneratorSettingsStateParser;
import com.ifengxue.plugin.state.ModuleSettings;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//com.ifengxue.plugin.gui.SelectTablesDialog.GeneratorRunner
@RequiredArgsConstructor
@Slf4j
public class GeneratorRunner implements Runnable { 
	private final DataSource dataSource ;

	public void run() {
		AutoGeneratorSettingsState autoGeneratorSettingsState = AutoGeneratorSettingsStateParser.parser();
		ModuleSettings moduleSettings = autoGeneratorSettingsState.getModuleSettings();
		 
		final Map<TableSchema, List<ColumnSchema>> mapping = new HashMap<>();
		final Object[][] directoryAndPackageNames = {
		            {
		                moduleSettings.isGenerateEntity(),
		                moduleSettings.getEntityParentDirectory(),
		                moduleSettings.getEntityPackageName()
		            },
		            {
		                moduleSettings.isGenerateRepository(),
		                moduleSettings.getRepositoryParentDirectory(),
		                moduleSettings.getRepositoryPackageName()
		            },
		            {
		                moduleSettings.isGenerateController(),
		                moduleSettings.getControllerParentDirectory(),
		                moduleSettings.getControllerPackageName()
		            },
		            {
		                moduleSettings.isGenerateMapperXml(),
		                moduleSettings.getMapperXmlParentDirectory(),
		                moduleSettings.getMapperXmlPackageName()
		            },
		            {
		                moduleSettings.isGenerateService(),
		                moduleSettings.getServiceParentDirectory(),
		                moduleSettings.getServicePackageName()
		            },
		            {
		                moduleSettings.isGenerateVO(),
		                moduleSettings.getVoParentDirectory(),
		                moduleSettings.getVoPackageName()
		            },
		            {
		                moduleSettings.isGenerateDTO(),
		                moduleSettings.getDtoParentDirectory(),
		                moduleSettings.getDtoPackageName()
		            },
		        };
		for (Object[] directoryAndPackageName : directoryAndPackageNames) {
					if (!(boolean) directoryAndPackageName[0]) {
						continue;
					}

					Path path = Paths.get((String) directoryAndPackageName[1],
							StringHelper.packageNameToFolder((String) directoryAndPackageName[2]));

					FileUtil.mkdirs(path);

				}
		
		try (Connection connection = this.dataSource.getConnection()) {
			process(mapping,autoGeneratorSettingsState , connection);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
	protected void process(final Map<TableSchema, List<ColumnSchema>> mapping ,
			final AutoGeneratorSettingsState autoGeneratorSettingsState,final Connection connection) throws SQLException {
		List<TableSchema> tableSchemaList = findDatabaseSchemas("otdb064", null, dataSource);
		List<Table> tableList = new ArrayList<>(tableSchemaList.size());	     
		for (TableSchema tableSchema : tableSchemaList) {
			String tableName = tableSchema.getTableName();
			log.info("TableSchema : {}", tableSchema.getTableName());
			
			String entityName = StringHelper.parseEntityName(tableName);
			log.info("ClassTableName : {}", entityName);
			
			String repositoryName = "testrepositoryName";
			boolean selected = true;

			tableList.add(Table.from(tableSchema, entityName, repositoryName, selected));
			List<ColumnSchema> columnSchemaList = ((MybatisGeneratorTableSchema) tableSchema).toColumnSchemas();
			mapping.put(tableSchema, columnSchemaList);			
		}
		
		final ModuleSettings moduleSettings = autoGeneratorSettingsState.getModuleSettings() ;
		
		List<GeneratorTask> tasks = getTasks(moduleSettings);
		for (GeneratorTask task : tasks) {
			if (task.sourceParser instanceof VelocityEngineAware) {
				((VelocityEngineAware) task.sourceParser).setVelocityEngine(VelocityUtil.getInstance(), "UTF-8");
			}
		}
		for (Table table : tableList) {
			table.setPackageName(moduleSettings.getEntityPackageName());
			if (table.getColumns() == null) {
				table.setColumns(findColumns(table, mapping, autoGeneratorSettingsState));
			}
			GeneratorConfig generatorConfig = buildConfig(autoGeneratorSettingsState, moduleSettings);
			for (GeneratorTask task : tasks) {
				if (!task.shouldRun) {
					continue;
				}
				String sourceCode = task.sourceParser.parse(generatorConfig, table);
				String filename = task.filenameMapping.apply(table);
				String parentPath = task.directory ;
				String packageName = task.packageName ;
				
				FileOutput.writeContent(generatorConfig, table, filename, parentPath, packageName, sourceCode);
			}
		}
	}
	protected List<TableSchema> findDatabaseSchemas(final String database, final String schema, final DataSource datasource)
			throws SQLException {
		try (Connection connection = datasource.getConnection()) {
			List<String> warnings = new ArrayList<>();
			Context context = new Context(ModelType.FLAT);
			DatabaseIntrospector introspector = new DatabaseIntrospector(context, connection.getMetaData(),
					new JavaTypeResolverDefaultImpl(), warnings);
			TableConfiguration tc = new TableConfiguration(context);
			tc.setCatalog(database);
			if (StringUtils.isNotBlank(schema)) {
				tc.setSchema(schema);
			}
			return introspector.introspectTables(tc).stream().map(MybatisGeneratorTableSchema::new)
					.collect(Collectors.toList());
		}
	}
	protected List<GeneratorTask> getTasks(final ModuleSettings moduleSettings){
		String fileExtension = "." + moduleSettings.getFileExtension();
		List<GeneratorTask> tasks = Arrays.asList(
	              GeneratorTask.builder()
	                  .shouldRun(true)
	                  .sourceParser(new EntitySourceParserV2())
	                  .directory(moduleSettings.getEntityParentDirectory())
	                  .packageName(moduleSettings.getEntityPackageName())
	                  .filenameMapping(t -> t.getEntityName() + fileExtension)
	                  .build());
		return tasks ;
	}
	protected GeneratorConfig buildConfig(final AutoGeneratorSettingsState autoGeneratorSettingsState,
			final ModuleSettings moduleSettings) {
		
		GeneratorConfig generatorConfig = new GeneratorConfig();
		generatorConfig.setDriverConfig(new DriverConfig());
		generatorConfig.setFileExtension(moduleSettings.getFileExtension());
		int lastIndex;
		String basePackageName = moduleSettings.getEntityPackageName();
		if ((lastIndex = basePackageName.lastIndexOf('.')) != -1) {
			basePackageName = basePackageName.substring(0, lastIndex);
		}
		generatorConfig.setTablesConfig(new TablesConfig()
		          .setBasePackageName(basePackageName)
		          .setEntityPackageName(moduleSettings.getEntityPackageName())
		          .setEnumSubPackageName(basePackageName + ".enums")
		          .setControllerPackageName(moduleSettings.getControllerPackageName())
		          .setServicePackageName(moduleSettings.getServicePackageName())
		          .setVoSuffixName(moduleSettings.getVoSuffixName())
		          .setVoPackageName(moduleSettings.getVoPackageName())
		          .setDtoSuffixName(moduleSettings.getDtoSuffixName())
		          .setDtoPackageName(moduleSettings.getDtoPackageName())
//		          .setIndent(getIndent())
//		          .setLineSeparator(getLineSeparator())
		          .setOrm(ORM.JPA)
		          .setExtendsEntityName(autoGeneratorSettingsState.getInheritedParentClassName())
		          .setRemoveTablePrefix(autoGeneratorSettingsState.getRemoveEntityPrefix())
		          .setRemoveFieldPrefix(autoGeneratorSettingsState.getRemoveFieldPrefix())
		          .setRepositoryPackageName(moduleSettings.getRepositoryPackageName())
		          .setSerializable(autoGeneratorSettingsState.isSerializable())
		          .setUseClassComment(autoGeneratorSettingsState.isGenerateClassComment())
		          .setUseFieldComment(autoGeneratorSettingsState.isGenerateFieldComment())
		          .setUseMethodComment(autoGeneratorSettingsState.isGenerateMethodComment())
		          .setUseDefaultValue(autoGeneratorSettingsState.isGenerateDefaultValue())
		          .setUseDefaultDatetimeValue(autoGeneratorSettingsState.isGenerateDatetimeDefaultValue())
		          .setUseWrapper(true)
		          .setUseLombok(autoGeneratorSettingsState.isUseLombok())
		          .setUseJava8DateType(autoGeneratorSettingsState.isUseJava8DateType())
		          .setUseFluidProgrammingStyle(autoGeneratorSettingsState.isUseFluidProgrammingStyle())
		          .setUseSwaggerUIComment(autoGeneratorSettingsState.isGenerateSwaggerUIComment())
		          .setUseJpaAnnotation(autoGeneratorSettingsState.isGenerateJpaAnnotation())
		          .setAddSchemeNameToTableName(autoGeneratorSettingsState.isAddSchemaNameToTableName())
		          .setUseJakartaEE(autoGeneratorSettingsState.isUseJakartaEE())
		          .setUseJpa(moduleSettings.isRepositoryTypeJPA())
		          .setUseMybatisPlus(moduleSettings.isRepositoryTypeMybatisPlus())
		          .setUseTkMybatis(moduleSettings.isRepositoryTypeTkMybatis())
		      );
		      generatorConfig.setPluginConfigs(Collections.emptyList());
      return generatorConfig ;		      
   }

	/**
	 * find columns
	 */
	private List<Column> findColumns(Table table, final Map<TableSchema, List<ColumnSchema>> mapping,
			final AutoGeneratorSettingsState autoGeneratorSettingsState) {

		List<ColumnSchema> columnSchemas = mapping.get(table.getRawTableSchema());
		if (columnSchemas == null) {
			return Collections.emptyList();
		}
		// column schema to column
		List<Column> columns = new ArrayList<>(columnSchemas.size());
		int sequence = 1;
		for (ColumnSchema columnSchema : columnSchemas) {
			Column column = ColumnUtil.columnSchemaToColumn(columnSchema,
					autoGeneratorSettingsState.getRemoveFieldPrefix(),
					autoGeneratorSettingsState.getIfJavaKeywordAddSuffix(), true,
					autoGeneratorSettingsState.isUseJava8DateType());
			column.setSequence(sequence++);
			column.setSelected(!autoGeneratorSettingsState.getIgnoredFields().contains(column.getFieldName()));
			if (column.isPrimary()) {
				table.setPrimaryKeyClassType(column.getJavaDataType());
				table.incPrimaryKeyCount();
			}
			columns.add(column);
		}
		return columns;
	}
	@Builder 
	private static class GeneratorTask {
	  private final boolean shouldRun;
	  private final SourceParser sourceParser;
	  private final String directory;
	  private final String packageName;
	  private final Function<Table, String> filenameMapping;

	}
}
