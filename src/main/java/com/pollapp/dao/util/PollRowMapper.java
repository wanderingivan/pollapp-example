package com.pollapp.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.pollapp.model.Poll;
import com.pollapp.model.User;

/**
 * Converts a result set row
 * to a Poll object with 
 * it's owner association set 
 * @see com.pollapp.model.Poll
 */
public class PollRowMapper implements RowMapper<Poll> {

	private final RowMapper<User> userMapper = new BeanPropertyRowMapper<>(User.class,false);
	
	@Override
	public Poll mapRow(ResultSet rs, int rowNum) throws SQLException {
		Poll p = new Poll(rs.getLong("poll_id"));
		p.setName(rs.getString("poll_name"));
		p.setVotes(rs.getLong("votes"));
		p.setDescription(rs.getString("description"));
		p.setOwner(userMapper.mapRow(rs,rs.getRow()));
		p.setCreated(rs.getDate("created"));
		return p;
	}

}
