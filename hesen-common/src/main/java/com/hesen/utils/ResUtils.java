package com.hesen.utils;

import com.hesen.enums.ResEnum;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;

/**
 * Created by hesen on 2017-10-23
 */

public class ResUtils {
	
	/**
	 * 响应成功
	 * @return
	 */
	public static JsonResponse success(){
		JsonResponse response = new JsonResponse();
		response.setCode(ResEnum.RES_CODE_SUCCESS.getCode());
		response.setErrmsg(ResEnum.RES_CODE_SUCCESS.getErrmsg());
		return response;
	}
	
	public static JsonObjectResponse success(Object data){
		JsonObjectResponse response = new JsonObjectResponse();
		response.setCode(ResEnum.RES_CODE_SUCCESS.getCode());
		response.setErrmsg(ResEnum.RES_CODE_SUCCESS.getErrmsg());
		response.setData(data);
		return response;
	}
	
	/**
	 * 响应失败
	 * @return
	 */
	public static JsonResponse fail(){
		JsonResponse response = new JsonResponse();
		response.setCode(ResEnum.RES_CODE_FAIL.getCode());
		response.setErrmsg(ResEnum.RES_CODE_FAIL.getErrmsg());
		return response;
	}
	
	public static JsonResponse fail(Object data){
		JsonObjectResponse response = new JsonObjectResponse();
		response.setCode(ResEnum.RES_CODE_FAIL.getCode());
		response.setErrmsg(ResEnum.RES_CODE_FAIL.getErrmsg());
		response.setData(data);
		return response;
	}
}
