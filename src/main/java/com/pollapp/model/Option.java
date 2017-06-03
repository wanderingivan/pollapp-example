package com.pollapp.model;

import java.util.Date;


public class Option {

	private long id;
	
	private String optionName;
	
	private long voteCount;
	
	private Date added;
	
	public Option(){}
	
	public Option(String name) {
		super();
		this.optionName = name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String name) {
		this.optionName = name;
	}

	public long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(long voteCount) {
		this.voteCount = voteCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Option [id=").append(id).append(", name=").append(optionName)
				.append(", votes=").append(voteCount).append("]");
		return builder.toString();
	}

    public Date getAdded() {
        return added;
    }
    
    public void setAdded(Date added){
        this.added = added;
    }
	
}
