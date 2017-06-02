package com.pollapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.pollapp.filter.AngularSpringSecurityCsrfAdapterFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth,DaoAuthenticationProvider authProvider)
			throws Exception {
		  auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .mvcMatchers(HttpMethod.POST,"/user").anonymous()
		    .mvcMatchers(HttpMethod.PUT, "/user/**").authenticated()
		    .mvcMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
		    .mvcMatchers(HttpMethod.GET, "/polls/**").permitAll()
		    .mvcMatchers(HttpMethod.POST, "/polls/*/vote/*").permitAll()
		    .mvcMatchers(HttpMethod.POST, "/polls/**").authenticated()
		    .mvcMatchers(HttpMethod.PUT, "/polls/**").authenticated()
		    .mvcMatchers(HttpMethod.DELETE, "/polls/**").authenticated()
		    .and()
		    .formLogin()
		    .and()
		    .csrf().csrfTokenRepository(csrfTokenRepository())
		    .and()
		    .httpBasic()
		    .and()
		    .addFilterAfter(new AngularSpringSecurityCsrfAdapterFilter(), CsrfFilter.class);
	}
	
	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		  repository.setHeaderName("X-XSRF-TOKEN");
		  return repository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
}
