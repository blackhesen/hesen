package com.hesen.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hesen.web.handler.AccessTokenInterceptor;


/**
 * 注册拦截器
 * Created by hesen on 2017-10-30
 */
@Configuration
public class AccessTokenConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public AccessTokenInterceptor accessTokenInterceptor(){
		return new AccessTokenInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessTokenInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/system/login", "/error/**", "/system/qr/generate");
	}
}
