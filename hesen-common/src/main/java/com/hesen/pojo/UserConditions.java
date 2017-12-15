package com.hesen.pojo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hesen on 2017-12-04
 */
public class UserConditions implements Serializable{
	
	public interface Add {}
	
	public interface Modify {}
	
	public interface Delete {}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	@NotBlank(message = "用户名不能为空", groups = {Add.class})
	private String username;
	
	@NotBlank(message = "密码不能为空", groups = {Add.class})
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
}
