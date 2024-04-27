package com.ifengxue.plugin;

import fastjdbc.NoPoolDataSource;  
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection; 
import java.util.ArrayList;
 
import java.util.List;
 
import javax.sql.DataSource;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.db.DatabaseIntrospector;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;


 
@Slf4j                         
public class DatabaseIntrospectorTest {

	@BeforeEach
	protected void setUp() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

  @Test
  public void testIntrospectTables() throws Exception {
//    DataSource dataSource = new NoPoolDataSource(
//        "jdbc:mysql://192.168.62.37:3306/otdb064?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8",
//        "myuser", "mypassword");
		DataSource dataSource = new NoPoolDataSource(
				"jdbc:mysql://localhost:3306/otdb064?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8",
				"myuser", "mypassword");

		try (Connection connection = dataSource.getConnection()) {
			List<String> warnings = new ArrayList<>();
			Context context = new Context(ModelType.FLAT);
			DatabaseIntrospector introspector = new DatabaseIntrospector(context, connection.getMetaData(),
					new JavaTypeResolverDefaultImpl(), warnings);
			TableConfiguration tc = new TableConfiguration(context);

			List<IntrospectedTable> tables = introspector.introspectTables(tc);
			System.out.println(tables.size());

			for (IntrospectedTable table : tables) {
				log.info("type: {}", table.getTableType());
				log.info("description: {}", table.getRemarks());

				if (table.getFullyQualifiedTable().getIntrospectedTableName().equals("t_complex")) {
					System.out.println();
				}
				List<IntrospectedColumn> columns = table.getAllColumns();
				for (IntrospectedColumn column : columns) {
					System.out.println(column.getActualColumnName());
				}
			}
		}
	}
}
