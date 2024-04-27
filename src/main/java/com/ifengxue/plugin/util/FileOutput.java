package com.ifengxue.plugin.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.ifengxue.plugin.entity.Table;

import com.ifengxue.plugin.generator.config.GeneratorConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileOutput {
	private static final String generatedPath = "code-generated-sources";
	public static void writeContent(GeneratorConfig generatorConfig, Table table, String filename, String parentPath,
			String packageName, String sourceCode) {
//		log.info("filename: {}", filename);
//		log.info("parentPath: {}", parentPath);
//		log.info("packageName: {}", packageName);
//		 log.info("sourceCode: {}" ,sourceCode);
		File packageDir = packageDir(packageName);
		File Unit = new File(packageDir, filename);
		
		try {
			FileUtils.writeStringToFile(Unit, sourceCode, StandardCharsets.UTF_8);
		} catch (IOException e) {
			log.error(e.getMessage(), e); 
		}
		System.out.print(packageDir.getAbsolutePath());
	}

	protected static File packageDir(final String packageName) {
		File targetClassesDir = new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		File targetDir = targetClassesDir.getParentFile();
		String newPath = StringUtils.replace(packageName, ".", File.separator);
		File packageDir = new File(targetDir, generatedPath + File.separator + newPath);
		return packageDir;
	}
}