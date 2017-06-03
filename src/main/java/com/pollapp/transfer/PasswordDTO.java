package com.pollapp.transfer;

public class PasswordDTO {
	
	private String oldPassword,
	               newPassword;

	
	public PasswordDTO(){}
	
	public PasswordDTO(String newPassword,String oldPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	
}
