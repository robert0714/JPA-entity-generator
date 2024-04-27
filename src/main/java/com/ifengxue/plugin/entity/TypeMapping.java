package com.ifengxue.plugin.entity;
 
import com.ifengxue.plugin.state.wrapper.ClassWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TypeMapping {
 
    private String dbColumnType;

     
    private Class<?> javaType;

    public static TypeMapping from(Entry<String, ClassWrapper> entry) {
        return new TypeMapping()
            .setDbColumnType(entry.getKey())
            .setJavaType(entry.getValue().getClazz());
    }

    public static List<TypeMapping> from(Map<String, ClassWrapper> map) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyList();
        }
        return map.entrySet().stream()
            .map(TypeMapping::from)
            .collect(Collectors.toList());
    }
}
