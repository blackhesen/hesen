package com.hesen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;

/**
 * Created by hesen on 2017-11-09
 */
public class MailUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);
	
	public static boolean sendMail(JavaMailSender mailSender, String from,String mailReceivers,String subject,String body) {
        if (StringUtils.hasText(mailReceivers)) {
            String[] mails = mailReceivers.split(";");
            return sendMail(mailSender, from, mails, subject, body);
        }
        return false;
    }


    public static boolean sendMail(JavaMailSender mailSender,String from, String[] mails,String subject,String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);//设置邮件发送者名称
            message.setTo(mails);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            LOG.info("********发送邮件成功*********");
            return  true;
        } catch (Exception ex) {
            LOG.error("发送邮件失败,邮箱 :{},{}", mails, ex);
        }
        return  false;
    }
	
}
