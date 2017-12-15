package com.hesen.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hesen.enums.ResEnum;
import com.hesen.exception.OwnException;
import com.hesen.service.LoginService;

/**
 * Created by hesen on 2017-10-30
 */
@Order(1)
public class AccessTokenInterceptor implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(AccessTokenInterceptor.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 页面渲染之后调用，一般用于资源清理操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.info("视图渲染完毕");
	}
	
	/**
	 * controller 执行之后，且页面渲染之前调用
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		logger.info("请求执行完毕");
	}
	
	/**
	 * controller执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		
		logger.info("登录检查拦截");
		
		//校验用户是否存在
		boolean hasUser = loginService.hasUser(request.getHeader("accessToken"));
		if (!hasUser) {
			throw new OwnException(ResEnum.ERROR_NO_LOGIN);
		}
		
		logger.info("登录检查通过");
		return true;
	}

}
