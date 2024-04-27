package com.ifengxue.plugin.util;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ifengxue.plugin.util.GeneratorRunner;

import fastjdbc.NoPoolDataSource;

class GeneratorRunnerTest {
	private GeneratorRunner runner;

	@BeforeEach
	protected void setUp() throws Exception {
//	    DataSource dataSource = new NoPoolDataSource(
//      "jdbc:mysql://192.168.62.37:3306/otdb064?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8",
//      "myuser", "mypassword");
		DataSource dataSource = new NoPoolDataSource(
				"jdbc:mysql://localhost:3306/otdb064?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8",
				"myuser", "mypassword");
		this.runner = new GeneratorRunner( dataSource);
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		this.runner.run();
	}

}
