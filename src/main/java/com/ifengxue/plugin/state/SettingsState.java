package com.ifengxue.plugin.state;
 
import com.ifengxue.plugin.state.wrapper.ClassWrapper; 
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils; 

@Slf4j
@Data 
public class SettingsState  {
 
    // template config

    /**
     * template id to template
     */
    private Map<String, String> templateIdToTemplate = new HashMap<>();

    // type config
    private boolean fallbackType = true;
    private boolean throwException;
     
    private Class<?> fallbackTypeClass = java.lang.String.class;
    /**
     * db type to java type
     */
    private Map<String, ClassWrapper> dbTypeToJavaType;
 
    public SettingsState getState() {
        return this;
    }
 
    public void loadState(  SettingsState state) {
       
        if (state.dbTypeToJavaType == null) {
            resetTypeMapping();
        }
        dbTypeToJavaType = ListOrderedMap.listOrderedMap(new CaseInsensitiveMap<>(dbTypeToJavaType));
    }

    public Map<String, ClassWrapper> getOrResetDbTypeToJavaType() {
        if (dbTypeToJavaType == null) {
            resetTypeMapping();
        }
        return dbTypeToJavaType;
    }

    public void resetTypeMapping() {
        dbTypeToJavaType = ListOrderedMap.listOrderedMap(new CaseInsensitiveMap<>());
        dbTypeToJavaType.put("CLOB", new ClassWrapper(String.class));
        dbTypeToJavaType.put("TEXT", new ClassWrapper(String.class));
        dbTypeToJavaType.put("VARCHAR", new ClassWrapper(String.class));
        dbTypeToJavaType.put("CHAR", new ClassWrapper(String.class));
        dbTypeToJavaType.put("JSON", new ClassWrapper(String.class));

        dbTypeToJavaType.put("TINYBLOB", new ClassWrapper(byte[].class));
        dbTypeToJavaType.put("BLOB", new ClassWrapper(byte[].class));
        dbTypeToJavaType.put("MEDIUMBLOB", new ClassWrapper(byte[].class));
        dbTypeToJavaType.put("LONGBLOB", new ClassWrapper(byte[].class));

        dbTypeToJavaType.put("ID", new ClassWrapper(Long.class));
        dbTypeToJavaType.put("BIGINT", new ClassWrapper(Long.class));
        dbTypeToJavaType.put("INTEGER", new ClassWrapper(Long.class));

        dbTypeToJavaType.put("TINYINT", new ClassWrapper(Integer.class));
        dbTypeToJavaType.put("SMALLINT", new ClassWrapper(Integer.class));
        dbTypeToJavaType.put("INT", new ClassWrapper(Integer.class));
        dbTypeToJavaType.put("MEDIUMINT", new ClassWrapper(Integer.class));

        dbTypeToJavaType.put("BIT", new ClassWrapper(Boolean.class));

        dbTypeToJavaType.put("FLOAT", new ClassWrapper(Float.class));

        dbTypeToJavaType.put("DOUBLE", new ClassWrapper(Double.class));

        dbTypeToJavaType.put("DECIMAL", new ClassWrapper(BigDecimal.class));

        dbTypeToJavaType.put("YEAR", new ClassWrapper(Short.class));

        dbTypeToJavaType.put("DATE", new ClassWrapper(java.sql.Date.class));

        dbTypeToJavaType.put("TIME", new ClassWrapper(Time.class));

        dbTypeToJavaType.put("DATETIME", new ClassWrapper(Date.class));
        dbTypeToJavaType.put("TIMESTAMP", new ClassWrapper(Date.class));

        // Postgre SQL
        dbTypeToJavaType.put("CHARACTER VARYING", new ClassWrapper(String.class));

        dbTypeToJavaType.put("BYTEA", new ClassWrapper(byte[].class));

        dbTypeToJavaType.put("BIGSERIAL", new ClassWrapper(Long.class));

        dbTypeToJavaType.put("REAL", new ClassWrapper(Float.class));

        dbTypeToJavaType.put("DOUBLE PRECISION", new ClassWrapper(Double.class));

        dbTypeToJavaType.put("NUMERIC", new ClassWrapper(BigDecimal.class));
    }

    public void putTemplate(String templateId, String template) {
        templateIdToTemplate.put(templateId, template);
    }
 
    public String loadTemplate(String templateId) {
        String template = templateIdToTemplate.get(templateId);
        if (!StringUtils.isBlank(template)) {
            return template;
        }
        return forceLoadTemplate(templateId);
    }

    public String forceLoadTemplate(String templateId) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(templateId)) {
            if (input == null) {
                log.warn("template " + templateId + " not exists.");
                return "";
            }
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("read template " + templateId + " error", e);
            return "";
        }
    }
}
