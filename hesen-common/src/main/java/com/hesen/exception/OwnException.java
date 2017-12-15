package com.hesen.exception;

import com.hesen.enums.ResEnum;

/**
 * 业务错误
 * Created by hesen on 2017-10-25
 */
public class OwnException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResEnum error;
	
	private String message;
	
	public OwnException(String message) {
		super();
		this.message = message;
	}

	public OwnException(ResEnum error) {
		super();
		this.error = error;
		this.message = error.getErrmsg();
	}

	public ResEnum getError() {
		return error;
	}

	public void setError(ResEnum error) {
		this.error = error;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
