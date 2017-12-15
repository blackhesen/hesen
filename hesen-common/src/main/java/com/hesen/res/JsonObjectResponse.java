package com.hesen.res;

import java.io.Serializable;

/**
 * Created by hesen on 2017-10-23
 */

public class JsonObjectResponse<T> extends JsonResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
