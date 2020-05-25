package com.demo.bean;
/**
 * the private field should be the same as we set in DataBase
 * @author Howins
 *
 */
public class UserBean {
	private String username;
	private String password;
	private int id;

	public UserBean() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
