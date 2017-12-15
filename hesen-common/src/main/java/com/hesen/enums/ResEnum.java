package com.hesen.enums;

/**
 * Created by hesen on 2017-10-23
 */

public enum ResEnum {
	
	//接口返回code
	RES_CODE_SUCCESS(0, 0, "成功"),
    RES_CODE_FAIL(0, 1, "失败"),
	
	//用户信息
	ERROR_USER_WRONG_PWD(1, 50000, "用户名或密码错误"),
	
	ERROR_NO_LOGIN(1, 50001, "请登录"),
	
	//系统异常
	SYSTEM_ERROR(99999, -1, "系统异常"),
	
	//短信通知
	ERROR_SERVER_RESPONSE_FAIL(20000, 20001, "服务器响应失败,请重新发送"),
	ERROR_CLIENT_REQUEST_FAIL(20000, 20002, "请求失败,请重新发送"),
	
	//邮件通知
	ERROR_MAIL_FAIL(30000, 30001, "请求失败,请重新发送"),
	;
	
	private final int parentCode;
	private final int code;
	private final String errmsg;

	private ResEnum(int parentCode, int code, String errmsg) {
		this.parentCode = parentCode;
		this.code = code;
		this.errmsg = errmsg;
	}
	
	public int getParentCode() {
		return parentCode;
	}

	public int getCode() {
		return code;
	}

	public String getErrmsg() {
		return errmsg;
	}
}
