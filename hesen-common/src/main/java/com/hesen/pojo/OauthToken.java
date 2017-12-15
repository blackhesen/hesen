package com.hesen.pojo;

import java.io.Serializable;

/**
 * Created by hesen on 2017-10-26
 */
public class OauthToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userNo;
	
    private String accessToken;
    
    private String refreshToken;
    
    private long accessTokenExpires;
    
    private Object data;
    
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public long getAccessTokenExpires() {
		return accessTokenExpires;
	}
	public void setAccessTokenExpires(long accessTokenExpires) {
		this.accessTokenExpires = accessTokenExpires;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
