package com.hesen.enums;

/**
 * Created by hesen on 2017-11-09
 */
public enum MailEnum {
	
	MAIL_SUBJECT("金诚逸活动正式开始"),
	MAIL_TEXT("我们的活动马上就要开始了，赶快报名参加吧！"),
	;
	private final String message;

	private MailEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
