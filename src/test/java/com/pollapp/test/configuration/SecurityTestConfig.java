package com.pollapp.test.configuration;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pollapp.dao.PollDao;
import com.pollapp.dao.UserDao;
import com.pollapp.model.Poll;
import com.pollapp.model.User;

@Configuration
@ComponentScan("com.pollapp.service")
@EnableTransactionManagement
public class SecurityTestConfig {
	
	@Bean
	public UserDao userDaoMock(){
		UserDao dao = Mockito.mock(UserDao.class);
		Mockito.when(dao.createUser(new User("a username","email","description","password"))).thenReturn((long)24);
		return dao;
	}
	
	@Bean
	public PollDao pollDaoMock(){
		PollDao dao = Mockito.mock(PollDao.class);
		Mockito.when(dao.createPoll(new Poll("name", null, null, null))).thenReturn((long)24);
		return dao;
	}
	
	@Bean
	Cache mutableAclCache(){
		return  new NoOpCacheManager().getCache("acl");
	}
	
	@Bean
	@Autowired
	public DataSourceTransactionManager transactionManager(DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	@Bean
	@Autowired
	public DaoAuthenticationProvider authenticationProvider(DataSource dataSource){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService(dataSource));
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}
	
	@Bean
	public UserDetailsService userDetailsService (DataSource dataSource){
		JdbcDaoImpl dao =  new JdbcDaoImpl();
		dao.setDataSource(dataSource);
		dao.setEnableAuthorities(true);
		dao.afterPropertiesSet();
		return dao;
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(12);
	}
	
}
