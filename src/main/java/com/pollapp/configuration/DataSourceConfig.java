package com.pollapp.configuration;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DataSourceConfig {

    @Value("${db.driver_class}")
	private String driverName;
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.user}")
	private String user;
	
	@Value("${db.password}")
	private String password;
	
	@Value("${db.min_pool_size}")
	private int minPoolSize;

	@Value("${db.max_pool_size}")
	private int maxPoolSize;
	
	@Bean
	public DataSource datasource() {
		ComboPooledDataSource datasource = new ComboPooledDataSource();
		try {
			datasource.setDriverClass(driverName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		datasource.setJdbcUrl(url);
		datasource.setUser(user);
		datasource.setPassword(password);
		datasource.setMinPoolSize(minPoolSize);
		datasource.setMaxPoolSize(maxPoolSize);
		return datasource;
	}
}
