package com.pollapp.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pollapp.model.User;

/**
 * Maps a result set a row to
 * a User object 
 *	@see com.pollapp.modell.User
 */
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setDescription(rs.getString("description"));
		user.setEmail(rs.getString("email"));
		user.setImagePath(rs.getString("imagePath"));
		user.setJoined(rs.getDate("joined"));
		return user;
	}

}
