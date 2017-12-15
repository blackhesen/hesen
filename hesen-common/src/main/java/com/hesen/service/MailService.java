package com.hesen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String username;
	
	public void sendMail(String sendTo, String subject, String text) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setFrom(username);
		msg.setTo(sendTo);
		msg.setSubject(subject);
		msg.setText(text);
		javaMailSender.send(msg);
		
	}
}
