package com.pollapp.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pollapp.model.Option;

/**
 * Maps a result set a row to
 * a Option object 
 *	@see com.pollapp.modell.Option
 */
public class OptionRowMapper implements RowMapper<Option> {

	@Override
	public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
		Option o = new Option();
		o.setId(rs.getLong("option_id"));
		o.setOptionName(rs.getString("option_name"));
		o.setVoteCount(rs.getLong("voteCount"));
		o.setAdded(rs.getDate("added"));
		return o;
	}

}
