package cn.ulane.emp.entity;

import java.io.Serializable;

public class User implements Serializable {
	private Integer id;
	private String password;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + "]";
	}
		
}
