package com.hesen.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hesen.pojo.LoginConditions;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;
import com.hesen.service.LoginService;


/**
 * Created by hesen on 2017-10-24
 */
//@Controller  Thymeleaf测试方法一
@RestController //使用RestController等于Controller和ResponseBody的组合，返回数据默认json格式
@RequestMapping(value = "/system")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录
	 * @param conditions
	 * @return
	 */
	@PostMapping(value = "/login")
	public JsonObjectResponse login(@Validated @RequestBody LoginConditions conditions){
		
		return loginService.login(conditions);
	}
	
	//Thymeleaf测试 	方法一：Controller + 返回String
//	@GetMapping(value = "/thymeleaf1")
// 	public String thymeleaf1() {
//		
//		return "register";
// 	}
	
	//Thymeleaf测试 	方法二：Controller + 返回String
//	@GetMapping(value = "/thymeleaf2")
// 	public ModelAndView thymeleaf12() {
//		ModelAndView mv = new ModelAndView("register");
//		return mv;
// 	}
	
	@PostMapping(value = "/logout")
	public JsonResponse logout(HttpServletRequest request){
		
		logger.info("请求接口：退出登录");
		
		String token = request.getHeader("accessToken");
		//检查用户是否存在
//		loginService.getUserNo(token);
		return loginService.logout(token);
	}
	
//	@PostMapping(value = "/logout")
//	public JsonResponse check(HttpServletRequest request){
//		
//		logger.info("请求接口：退出登录");
//		
//		String token = request.getHeader("accessToken");
//		//检查用户是否存在
//		return loginService.logout(token);
//	}
}
