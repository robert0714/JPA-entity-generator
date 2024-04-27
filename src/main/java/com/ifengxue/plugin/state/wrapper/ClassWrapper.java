package com.ifengxue.plugin.state.wrapper;
 
import lombok.Data;

@Data
public class ClassWrapper {
 
    private Class<?> clazz;

    public ClassWrapper() {
    }

    public ClassWrapper(Class<?> clazz) {
        this.clazz = clazz;
    }
}
