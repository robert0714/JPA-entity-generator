package com.ifengxue.plugin.generator.merge;

import com.ifengxue.plugin.entity.Table;
import com.ifengxue.plugin.generator.config.GeneratorConfig; 

public interface SourceFileMerger {

    boolean tryMerge(GeneratorConfig generatorConfig, Table table );

    boolean isMergeSupported();
}
