package codegen; 

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.ifengxue.plugin.util.GeneratorRunner;

import codegen.config.ConfigProps;
import fastjdbc.NoPoolDataSource;
 
 
@SpringBootApplication(exclude = {
	    DataSourceAutoConfiguration.class, 
	    DataSourceTransactionManagerAutoConfiguration.class, 
	    HibernateJpaAutoConfiguration.class
	})
public class Main implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		DataSource dataSource = new NoPoolDataSource(props.getProperties().getProperty("db.jdbcurl"),
//				props.getProperties().getProperty("db.user"), props.getProperties().getProperty("db.psswd"));
//		GeneratorRunner runner = new GeneratorRunner(dataSource);
		runner.run();
	}
//	@Autowired
//	private ConfigProps props; 
	@Autowired
	private GeneratorRunner runner;

}
