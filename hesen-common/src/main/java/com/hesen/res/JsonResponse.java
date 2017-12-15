package com.hesen.res;

import java.io.Serializable;

public class JsonResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
	private String errmsg;
	
	public JsonResponse() {
		super();
	}

	public JsonResponse(Integer code, String errmsg) {
		super();
		this.code = code;
		this.errmsg = errmsg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
