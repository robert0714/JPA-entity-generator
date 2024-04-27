package codegen.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import lombok.Data; 
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:application.properties") 
@ConfigurationProperties(prefix = "db", ignoreInvalidFields = true)
@Data
@Slf4j
public class ConfigProps {
	private final ApplicationContext ctx;
	private Properties properties;

	public ConfigProps(final ApplicationContext ctx) {
		this.ctx = ctx;
		this.properties = new Properties();
		try (InputStream inputStream = this.ctx.getResource("classpath:application.properties").getInputStream();) {
			this.properties.load(inputStream);
		} catch (IOException e) {
			log.error(e.getMessage(), e);

		}
	} 
}
