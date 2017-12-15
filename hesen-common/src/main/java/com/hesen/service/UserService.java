package com.hesen.service;

import java.util.List;

import com.hesen.dto.PageDTO;
import com.hesen.pojo.UserConditions;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;

/**
 * Created by hesen on 2017-10-30
 */
public interface UserService {
	
	JsonObjectResponse<PageDTO> queryUserList();
	
	/**
	 * mybatis拦截器实现分页
	 * @param i
	 * @return
	 */
	JsonObjectResponse<PageDTO> queryUserListByPageInterceptor(int currentPage);
	
	/**
	 * 批量增加
	 * @param conditions
	 * @return
	 */
	JsonResponse addUser(List<UserConditions> conditions);
	
}
