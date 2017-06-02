package com.pollapp.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Poll {
	
	private long id;
	
	@NotNull
	@Size(min=6,max=50)
	private String name;
	
	private User owner;
	
	private String description;
	
	private List<Option> options;

	private Date created;
	
	private long votes;

	public Poll(){}
	
	public Poll(long id){
		this.id = id;
	}
	
	public Poll(String name, List<Option> options, User owner, String description) {
		super();
		this.name = name;
		this.owner = owner;
		this.options = options;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public void addOption(Option o){
		if (options == null){
			options = new ArrayList<Option>();
		}
		options.add(o);
	}
	
	public void removeOption(Option o){
		if (options == null){return;}
		options.remove(o);
	}
	
	public void setVotes(long votes){
		this.votes = votes;
	}
	
	public long getVotes(){
		return votes;
	}

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Poll [id=").append(id).append(", name=").append(name).append(", owner=").append(owner)
               .append(", description=").append(description).append(", options=").append(options).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Poll other = (Poll) obj;
        if (id != other.id) return false;
        return true;
    }
    
}
