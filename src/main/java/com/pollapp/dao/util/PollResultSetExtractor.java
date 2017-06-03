package com.pollapp.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.pollapp.model.Option;
import com.pollapp.model.Poll;

/**
 * ResultSetExtractor implementation
 * that converts a result set to a list of polls
 * with their associations
 * @see com.pollapp.model.Poll
 *
 */
public class PollResultSetExtractor implements ResultSetExtractor<List<Poll>> {

	private final RowMapper<Poll> pollMapper = new PollRowMapper();
	
	private final RowMapper<Option> optionMapper = new OptionRowMapper();
	
	@Override
	public List<Poll> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<Long,Poll> polls = new HashMap<>();
		while(rs.next()){
			Poll p = polls.get(rs.getLong("poll_id"));
			if(p == null){
				p = pollMapper.mapRow(rs, rs.getRow());
				polls.put(p.getId(), p);
			}
			if(rs.getLong("option_id") != 0){
				p.addOption(optionMapper.mapRow(rs, rs.getRow()));
			}

		}
		return new ArrayList<>(polls.values());
	}
	

}
