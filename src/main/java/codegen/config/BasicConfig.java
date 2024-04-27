package codegen.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ifengxue.plugin.util.GeneratorRunner;

import fastjdbc.NoPoolDataSource;

@org.springframework.context.annotation.Configuration
public class BasicConfig {
	@Bean
	@ConditionalOnMissingBean
	public ConfigProps createConfigProps(final ApplicationContext ctx) {
		return new ConfigProps(  ctx);
	}
	@Bean
	@ConditionalOnMissingBean
	public GeneratorRunner createGeneratorRunner(ConfigProps props) {
		String jdbcUrl = props.getProperties().getProperty("db.jdbcurl");
		String user = props.getProperties().getProperty("db.user");
		String psswd = props.getProperties().getProperty("db.psswd");
		DataSource dataSource = new NoPoolDataSource(jdbcUrl, user, psswd);
		return new GeneratorRunner( dataSource);
	}
}
