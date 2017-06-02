package com.pollapp.test.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.h2.tools.RunScript;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class DatabaseTesterConfig {

	@Bean
	public IDatabaseTester tester() throws Exception{
	    createSchema();
		return new DataSourceDatabaseTester(testDataSource());
	}
	
	@Bean
	public DataSource testDataSource(){
		DriverManagerDataSource source = new DriverManagerDataSource();
		
		source.setDriverClassName("org.h2.Driver");
		source.setUrl("jdbc:h2:mem:pollapp;MODE=mysql;DB_CLOSE_DELAY=-1;");
		source.setUsername("sa");
		source.setPassword("sa");
		
		return source;
	}
	
	protected void  createSchema() throws SQLException {
	    RunScript.execute("jdbc:h2:mem:pollapp;MODE=mysql;DB_CLOSE_DELAY=-1;", "sa","sa", "classpath:scripts/sql/create_database.sql",UTF_8,false);
	    RunScript.execute("jdbc:h2:mem:pollapp;MODE=mysql;DB_CLOSE_DELAY=-1;", "sa","sa", "classpath:scripts/sql/create_procedures_h2.sql",UTF_8,false);
	}
}
