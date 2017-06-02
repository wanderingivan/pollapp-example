
CREATE ALIAS IF NOT EXISTS  addvote AS $$
void addVote(Connection conn, long optId, long pollId) throws SQLException {

	conn.setAutoCommit(false);
	
	PreparedStatement pollSt = conn.prepareStatement("UPDATE polls SET votes = votes + 1 WHERE poll_id = ?;");
	PreparedStatement optSt = conn.prepareStatement("UPDATE options SET votes = votes + 1 WHERE option_id = ?;");
	
	pollSt.setLong(1,pollId);
	optSt.setLong(1,optId);
	
	pollSt.executeUpdate();
	optSt.executeUpdate();
	
	conn.commit();
}
$$;

CREATE ALIAS IF NOT EXISTS createpoll AS $$
ResultSet createPoll(Connection conn, String pollName, String optionNames,String description,String ownerUsername) throws SQLException {

	conn.setAutoCommit(false);
	
	PreparedStatement pollCStmt = conn.prepareStatement("INSERT INTO polls(poll_name,description,owner_id,created) VALUES(?,?,(SELECT user_id FROM users WHERE username=?),CURRENT_TIMESTAMP())");
	PreparedStatement optionCStmt = conn.prepareStatement("INSERT INTO options(option_name,poll_id) VALUES (?,?)");
	
	pollCStmt.setString(1,pollName);
	pollCStmt.setString(2,description);
	pollCStmt.setString(3,ownerUsername);
	
	pollCStmt.executeUpdate();
	
	ResultSet rs = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID() as pollId;");
	rs.next();
	Long pollId = rs.getLong("pollId");
	
	String [] optnames = optionNames.split(",");

	for(String name : optnames){
		optionCStmt.setString(1,name);
		optionCStmt.setLong(2,pollId);
		optionCStmt.executeUpdate();
	}

   	
    PreparedStatement ps =  conn.prepareStatement("select poll_id as pollId from polls where poll_id = ? ");
    ps.setLong(1,pollId);
    rs = ps.executeQuery();
    
    conn.commit();
	
	return rs;	
}
$$;