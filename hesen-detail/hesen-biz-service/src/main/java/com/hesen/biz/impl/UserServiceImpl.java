package com.hesen.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hesen.biz.dao.UserMapper;
import com.hesen.dto.PageDTO;
import com.hesen.model.User;
import com.hesen.page.Page;
import com.hesen.pojo.UserConditions;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;
import com.hesen.service.UserService;
import com.hesen.utils.Constants;
import com.hesen.utils.ResUtils;
import com.hesen.utils.StringUtil;

/**
 * Created by hesen on 2017-10-30
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public JsonObjectResponse<PageDTO> queryUserList() {
		
		List<User> userList = userMapper.queryUser();
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.setResult(userList);
		pageDTO.setPage(null);
		
		return ResUtils.success(pageDTO);
	}

	@Override
	public JsonObjectResponse<PageDTO> queryUserListByPageInterceptor(int currentPage) {
		
		Page<User> pages = new Page<>(currentPage,Constants.PAGESIZE);
		
		List<User> userlist = userMapper.queryUserByPage(pages);
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.setResult(userlist);
		pageDTO.setPage(pages);
		
		return ResUtils.success(pageDTO);
	}

	@Override
	public JsonResponse addUser(List<UserConditions> conditions) {
		
		JsonResponse response = new JsonResponse();
		
		for (UserConditions userConditions : conditions) {
			userConditions.setId(StringUtil.genUUID());
		}
		
		userMapper.batchInsert(conditions);
		
		
		return ResUtils.success();
	}
}
