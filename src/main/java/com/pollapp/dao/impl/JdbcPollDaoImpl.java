package com.pollapp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.pollapp.dao.PollDao;
import com.pollapp.dao.util.PollResultSetExtractor;
import com.pollapp.dao.util.PollRowMapper;
import com.pollapp.exception.MissingPollException;
import com.pollapp.model.Option;
import com.pollapp.model.Poll;

/**
 * A PollDao implementation
 * that uses Spring framework's
 * JDBC support to access a MySql Database
 *
 */

@Repository
public class JdbcPollDaoImpl extends AbstractJdbcDao implements PollDao {
			
    private static final String	createOptionStatement = "INSERT INTO options(option_name,poll_id,added) VALUES(?,?,CURRENT_TIMESTAMP())",
    		
    							pollSearchQuery = "SELECT p.poll_id, "
										               + "p.poll_name, "
										               + "p.votes, "
										               + "p.created , "
										               + "p.description, "
										               + "u.user_id as id, "
										               + "u.username,"
										               + "u.description,"
										               + "u.imagePath "
										          + "FROM polls p "
										        	   + "INNER JOIN users u "
										        	   + "ON u.user_id = p.owner_id ",
										        	   
								pollQuery =       "SELECT p.poll_id, "
										               + "p.poll_name, "
										               + "p.votes, "
										               + "p.description, "
										               + "p.created , "
										               + "u.user_id as id, "
										               + "u.username,"
										               + "u.description,"	
										               + "u.imagePath,"
										               + "o.option_id, "
										               + "o.option_name, "
										               + "o.votes as voteCount, "
										               + "o.added "
										          + "FROM polls p "
										        	   + "INNER JOIN users u "
										        	   + "ON u.user_id = p.owner_id "
										        	   + "LEFT JOIN options o "
										        	   + "ON o.poll_id = p.poll_id "
										          + "WHERE p.poll_id = ?",
										          
						        updatePollStatement = "UPDATE polls SET poll_name = ?, description = ? WHERE poll_id = ?",
						        
						        updateOptionStatement = "UPDATE options SET option_name = ? WHERE option_id = ?",
										        	 
								deletePollStatement = "DELETE FROM polls WHERE poll_id = ?";
	
	private final ResultSetExtractor<List<Poll>> pollExtractor;
	private final RowMapper<Poll> pollMapper;
	
	private final SimpleJdbcCall createPollCall,
								       voteCall; 
	
	@Autowired
	public JdbcPollDaoImpl(JdbcTemplate template) {
		super(template);
		createPollCall = new SimpleJdbcCall(template).withProcedureName("createPoll")
												     .declareParameters(new SqlParameter("pollName",Types.VARCHAR),
												    		 			new SqlParameter("optionNames",Types.VARCHAR),
												    		 			new SqlParameter("description",Types.VARCHAR),
																 		new SqlParameter("ownerUsername",Types.VARCHAR),
																 		new SqlReturnResultSet("pollId", (rs) -> { rs.next(); return rs.getLong("pollId");}));
		
		voteCall = new SimpleJdbcCall(template).withProcedureName("addVote")
				                               .declareParameters(new SqlParameter("optionId",Types.BIGINT),
				                            		   			  new SqlParameter("pollId",Types.BIGINT));
		
		pollExtractor = new PollResultSetExtractor();
		pollMapper = new PollRowMapper();
	}

	@Override
	public long createPoll(Poll poll) {
		return (long) createPollCall.execute(new Object[]{poll.getName(),
				                                          convertToCsv(poll.getOptions()),
				                                          poll.getDescription(),
				                                          poll.getOwner().getUsername()})
				                    .get("pollId");
	}



	@Override
	public void updatePoll(Poll poll) {
		template.update(updatePollStatement,new Object[]{poll.getName(),poll.getDescription(),poll.getId()});
		Map<Boolean,List<Option>> options = poll.getOptions()                                             // Partition out the new options from the simply updated
		                                   .stream()                                                      // ones using the predicate that new options will not have added date set yet
		                                   .collect(Collectors.partitioningBy(o -> o.getAdded() != null));
		updateOptions(options.get(true));
		insertOptions(options.get(false),poll.getId());
	}


	@Override
	public Poll retrievePollById(long pollId) {
		List<Poll> polls = template.query(pollQuery, new Object[]{pollId},pollExtractor);
		if(polls == null || polls.size() == 0){
			throw new MissingPollException("Missing poll with id: "+pollId);
		}
		return polls.get(0);
		
	}
	
	@Override
	public List<Poll> retrieveRecentPolls(){
		return template.query(buildPollSearchQuery("ORDER BY p.poll_id DESC LIMIT 4"), pollMapper);
	}

	@Override
	public List<Poll> retrievePollByName(String pollName) {
		throw new UnsupportedOperationException("Remove from spec");
	}

	@Override
	public List<Poll> loadPolls(Long index) {
		return template.query(buildPollSearchQuery("ORDER BY p.poll_id DESC LIMIT 4 OFFSET ? "),new Object[]{index}, pollMapper);
	}

	@Override
	public void addOption(Option o, long pollId) {
		template.update(createOptionStatement, new Object[]{o.getOptionName(),pollId});
	}

	@Override
	public void deletePoll(long pollId) {
		template.update(deletePollStatement,new Object[]{pollId});
	}

	@Override
	public List<Poll> retrieveUserPolls(long userId) {
		return (List<Poll>) template.query(buildPollSearchQuery("WHERE u.user_id = ?"), new Object[]{userId},pollMapper);
	}
	
	@Override
	public void updateOption(Option o){
		template.update(updateOptionStatement,new Object[]{o.getOptionName(),o.getId()});
	}

	@Override
	public void vote(long optionId, long pollId) {
		voteCall.execute(new Object[]{optionId,pollId});
	}
	
	@Override
	public List<Poll> findPolls(String name) {
		StringBuilder sb = new StringBuilder("%").append(name.toLowerCase())
		                                         .append("%");
		return (List<Poll>) template.query(buildPollSearchQuery("WHERE LOWER(p.poll_name) LIKE ?"), new Object[]{sb.toString()},pollMapper);
	
	}
	
	private String buildPollSearchQuery(String predicate){
		return pollSearchQuery.concat(predicate);
	}	
	
	/**
	 * Converts a list of options
	 * to a csv string of option names
	 * to be used in a stored procedure
	 * @param options
	 * @return
	 */
	private String convertToCsv(List<Option> options) {
	    return options.stream()
	                  .map(Option::getOptionName)
	                  .collect(Collectors.joining(",","",""));
	}

	/**
	 * Executes a batch update on
	 * a list of existing options
	 * @param options
	 */
	private void updateOptions(final List<Option> options) {
		template.batchUpdate(updateOptionStatement, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Option option = options.get(i);
				ps.setString(1, option.getOptionName());
				ps.setLong(2, option.getId());
			}
			
			@Override
			public int getBatchSize() {
			    return options.size() > 25 ? 25 : options.size();
			}
		});
	}
	
	private void insertOptions(final List<Option> options,final long pollId){
		template.batchUpdate(/*"INSERT INTO options(option_name,poll_id,added) VALUES(?,?,?)"*/ createOptionStatement, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Option option = options.get(i);
				ps.setString(1, option.getOptionName());
				ps.setLong(2, pollId);
				//ps.setDate(3, new Date(System.currentTimeMillis()));
			}
			
			@Override
			public int getBatchSize() {
			    return options.size() > 25 ? 25 : options.size();
			}
		});		
	}



}
