package com.hesen.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hesen.enums.MailEnum;
import com.hesen.enums.ResEnum;
import com.hesen.exception.OwnException;
import com.hesen.res.JsonResponse;
import com.hesen.utils.MailUtil;
import com.hesen.utils.ResUtils;

/**
 * Created by hesen on 2017-10-30
 */
@RestController
@RequestMapping(value = "/system/mail")
public class MailController {
	
	private Logger logger = LoggerFactory.getLogger(MailController.class);
	
//	@Autowired
//	MailService mailService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@PostMapping(value = "/send")
	public JsonResponse list(){
		
		String sendTo = "820536869@qq.com";//可以从数据库获取
		
		logger.info("发送邮件，邮箱{}",sendTo);
		try {
			//方式一：mailService类发送邮件
//			mailService.sendMail(sendTo, MailEnum.MAIL_SUBJECT.getMessage(), MailEnum.MAIL_TEXT.getMessage());
			
			//工具类：发送邮件
			MailUtil.sendMail(mailSender, from, sendTo, MailEnum.MAIL_SUBJECT.getMessage(), MailEnum.MAIL_TEXT.getMessage());
		} catch (Exception e) {
			logger.error("邮件发送失败：邮箱{},{}",sendTo,e.getMessage());
			throw new OwnException(ResEnum.ERROR_MAIL_FAIL);
		}
		logger.info("********邮件发送成功********");
		return ResUtils.success();
	}
}
