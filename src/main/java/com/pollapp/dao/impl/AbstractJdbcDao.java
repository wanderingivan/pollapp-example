package com.pollapp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractJdbcDao {

	protected JdbcTemplate template;
	
	@Autowired
	public AbstractJdbcDao(JdbcTemplate template) {
		super();
		this.template = template;
	}
	
}
