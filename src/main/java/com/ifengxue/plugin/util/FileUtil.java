package com.ifengxue.plugin.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
 
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	private static final String generatedPath = "code-generated-sources";
	private static final String REPLACE_BY = "$PROJECT_DIR$\\src\\main\\java\\";

	public static void mkdirs(Path path) {
		log.info("path: {}", path.toString());
		String original = StringUtils.remove(path.toString(), REPLACE_BY);
		String newPath = StringUtils.replace(original, "\\", File.separator);
		String newLoaction = generatedPath + File.separator + newPath;
		log.info("newLoaction: {}", newLoaction);
		File targetClassesDir = new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		File targetDir = targetClassesDir.getParentFile();
		File generateDir = new File(targetDir, newLoaction);
		if (!generateDir.exists()) {
//			generateDir.mkdir();
			try {
				FileUtils.forceMkdir(generateDir);
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
}
