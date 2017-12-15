package com.hesen.service;

import com.hesen.pojo.LoginConditions;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;

public interface LoginService {
	
	/**
	 * 登录
	 * @param conditions
	 * @return
	 */
	JsonObjectResponse login(LoginConditions conditions);

	JsonResponse logout(String token);
	
	String getUserNo(String token);
	
	boolean hasUser(String token);
}
