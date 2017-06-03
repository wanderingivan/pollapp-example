package com.pollapp.dao.impl;

import java.sql.Date;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pollapp.dao.UserDao;
import com.pollapp.dao.util.UserRowMapper;
import com.pollapp.exception.MissingUserException;
import com.pollapp.model.User;

/**
 * A UserDao implementation
 * that uses Spring framework's
 * JDBC support to access a database
 *
 */

@Repository
public class JdbcUserDaoImpl extends AbstractJdbcDao implements UserDao{

	private static final String createUserStatement = "INSERT INTO users(username,email,password,description,joined) VALUES (?,?,?,?,?)",
								
								searchUsersStatement = "SELECT u.user_id as id, "
										                    + "u.username, "
										                    + "u.description, "
										                    + "u.imagePath "
										               + "FROM users u "
										              + "WHERE u.username LIKE ? LIMIT 20",
								
								userByUsernameQuery = "SELECT u.user_id, "
										                   + "u.username, "
										                   + "u.password, "
										                   + "u.email, "
										                   + "u.description, "
										                   + "u.imagePath, "
										                   + "u.joined "
										              + "FROM users u "
										             + "WHERE u.username = ?",
								
								userByIdQuery = "SELECT u.user_id, "
										             + "u.username, "
										             + "u.password, "
										             + "u.email, "
										             + "u.description, "
								                     + "u.imagePath, "
								                     + "u.joined "
										        + "FROM users u "
										       + "WHERE user_id = ?",
								
								updateUserStatement = "UPDATE users SET username = ?, email = ?, description = ? WHERE user_id = ?",
								
								deleteUserStatement = "DELETE FROM users WHERE user_id = ?",
								
								insertAuthorityStatement = "INSERT INTO authorities(username,authority) VALUES(?,'ROLE_USER')";
	
	private final PreparedStatementCreatorFactory insertStatementFactory;
	
	private final RowMapper<User> userMapper; 
	
	@Autowired
	public JdbcUserDaoImpl(JdbcTemplate template){
		super(template);
		insertStatementFactory = new PreparedStatementCreatorFactory(createUserStatement,
														             new int []{Types.VARCHAR,Types.VARCHAR,
																			    Types.VARCHAR,Types.VARCHAR,Types.DATE});
		insertStatementFactory.setReturnGeneratedKeys(true);
		userMapper =  new UserRowMapper();
	}

	@Override
	public long createUser(User user) {
		KeyHolder generatedId = new GeneratedKeyHolder();

		template.update(insertStatementFactory.newPreparedStatementCreator(new Object[]{user.getUsername(),user.getEmail(),
																		                user.getPassword(),user.getDescription(),new Date(System.currentTimeMillis())}),
																		   generatedId);
		template.update(insertAuthorityStatement, new Object []{user.getUsername()});
		return (long) generatedId.getKey();
	}

	@Override
	public void updateUser(User user) {
		template.update(updateUserStatement,new Object[]{user.getUsername(),user.getEmail(),
										                 user.getDescription(),user.getId()});
	}

	@Override
	public List<User> findUsers(String username) {
		String query = new StringBuilder("%").append(username)
											 .append("%")
											 .toString();
		return template.query(searchUsersStatement,new Object[]{query},new BeanPropertyRowMapper<User>(User.class,false));
	}

	@Override
	public User retrieveUserByUsername(String username) {
		
		try {
			return template.queryForObject(userByUsernameQuery, new Object[]{username},userMapper);
		}catch(EmptyResultDataAccessException ex){
			throw new MissingUserException(ex);
		}
	}

	@Override
	public User retrieveUserById(long userId) {
		try{
			return template.queryForObject(userByIdQuery, new Object[]{userId},userMapper);
		}catch(EmptyResultDataAccessException ex){
			throw new MissingUserException(ex);
		}
	}

	@Override
	public void deleteUser(long userId) {
		template.update(deleteUserStatement,new Object[]{userId});
	}

	@Override
	public void changePassword(String principal,String newPassword){
		template.update("UPDATE users SET password = ? WHERE username = ?",new Object[]{newPassword,principal});
	}
	
	@Override
	public String getPassword(String principal){
	    return template.queryForObject("SELECT password FROM users WHERE username = ?",new Object[]{principal}, String.class);
	}
}
