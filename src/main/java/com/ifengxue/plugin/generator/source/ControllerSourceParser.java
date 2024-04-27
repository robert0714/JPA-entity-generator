package com.ifengxue.plugin.generator.source;

import com.ifengxue.plugin.Constants;
import com.ifengxue.plugin.entity.Table;
import com.ifengxue.plugin.generator.config.GeneratorConfig;
import com.ifengxue.plugin.generator.config.TablesConfig; 
import com.ifengxue.plugin.util.StringHelper;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

public class ControllerSourceParser extends AbstractIDEASourceParser {

    @Override
    protected String parse(GeneratorConfig config, Table table, Supplier<String> templateProvider) {
        VelocityContext context = new VelocityContext();
        TablesConfig tablesConfig = config.getTablesConfig();
        context.put("table", table);
        context.put("tablesConfig", tablesConfig);
        context.put("package", StringUtils.trimToEmpty(tablesConfig.getControllerPackageName()));
        context.put("comment", StringUtils.trimToEmpty(table.getTableComment()));
        context.put("basePath", StringHelper.firstLetterLower(table.getEntityName()));
        context.put("simpleName", StringUtils.trimToEmpty(table.getControllerName()));
        context.put("entitySimpleName", table.getEntityName());
        context.put("serviceName", table.getServiceName());
        context.put("serviceVariableName", StringHelper.firstLetterLower(table.getServiceName()));
        context.put("primaryKeyDataType", table.getPrimaryKeyClassType().getSimpleName());
        context.put("useJakartaEE", config.getTablesConfig().isUseJakartaEE()); 
        return evaluate(context, templateProvider);
    }

    @Override
    protected String getTemplateId() {
        return Constants.CONTROLLER_TEMPLATE_ID;
    }
}
