package com.ifengxue.plugin.generator.source;

import com.ifengxue.plugin.Constants;
import com.ifengxue.plugin.TemplateManager;
import com.ifengxue.plugin.entity.Table;
import com.ifengxue.plugin.generator.config.GeneratorConfig;
import com.ifengxue.plugin.generator.config.TablesConfig;
import com.ifengxue.plugin.util.StringHelper;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

public class ServiceSourceParser extends AbstractIDEASourceParser {

    @Override
    public String parse(GeneratorConfig config, Table table) {
        TablesConfig tablesConfig = config.getTablesConfig();
        String templateId;
        if (tablesConfig.isUseMybatisPlus()) {
            templateId = Constants.MYBATIS_PLUS_SERVICE_TEMPLATE_ID;
        } else if (tablesConfig.isUseTkMybatis()) {
            templateId = Constants.TK_MYBATIS_SERVICE_TEMPLATE_ID;
        } else {
            templateId = getTemplateId();
        }
        return parse(config, table,
            () -> TemplateManager.getInstance()
                .loadTemplate(config.getFileExtension(), templateId));
    }

    @Override
    protected String parse(GeneratorConfig config, Table table, Supplier<String> templateProvider) {
        VelocityContext context = new VelocityContext();
        TablesConfig tablesConfig = config.getTablesConfig();
        context.put("table", table);
        context.put("tablesConfig", tablesConfig);
        context.put("package", StringUtils.trimToEmpty(tablesConfig.getServicePackageName()));
        context.put("simpleName", table.getServiceName());
        context.put("repositoryName", table.getRepositoryName());
        context.put("repositoryVariableName",
            StringHelper.firstLetterLower(table.getRepositoryName()));
        context.put("primaryKeyDataType", table.getPrimaryKeyClassType().getSimpleName());
        context.put("entitySimpleName", table.getEntityName());
        context.put("useJakartaEE", config.getTablesConfig().isUseJakartaEE());
        return evaluate(context, templateProvider);
    }

    @Override
    protected String getTemplateId() {
        return Constants.JPA_SERVICE_TEMPLATE_ID;
    }
}
