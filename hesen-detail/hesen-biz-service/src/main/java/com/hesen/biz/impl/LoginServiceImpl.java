package com.hesen.biz.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hesen.biz.dao.UserMapper;
import com.hesen.dto.LoginDTO;
import com.hesen.enums.ResEnum;
import com.hesen.exception.OwnException;
import com.hesen.model.User;
import com.hesen.pojo.LoginConditions;
import com.hesen.pojo.OauthToken;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;
import com.hesen.service.LoginService;
import com.hesen.utils.ResUtils;
import com.hesen.utils.StringUtil;


@Service
@Transactional
public class LoginServiceImpl implements LoginService{
	
	private static final String PREFIX = "ACCESS_TOKEN:";
	
	private static final Integer EXPIRE_MINUTES = 60 * 24 * 30;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public JsonObjectResponse login(LoginConditions conditions) {
		JsonObjectResponse<LoginDTO> response = new JsonObjectResponse<>();
		User user = userMapper.findByUserName(conditions.getUsername());
		
        if (conditions.getPassword() == null || user == null
                || !conditions.getPassword().equals(user.getPassword())) {
        	throw new OwnException(ResEnum.ERROR_USER_WRONG_PWD);
        }

        LoginDTO dto = new LoginDTO();
        dto.setUsername(user.getUsername());
        dto.setAccessToken(createToken(user).getAccessToken());
        
        return ResUtils.success(dto);
	}
	
	private OauthToken createToken(User user){
		
		OauthToken tokenObj = new OauthToken();
		
		String token = StringUtil.genUUID();
		tokenObj.setUserNo(user.getUsername());
		tokenObj.setAccessToken(token);
		tokenObj.setRefreshToken(token);
		tokenObj.setData(user);
		
		redisTemplate.opsForValue().set(PREFIX + token, JSON.toJSONString(tokenObj), Long.valueOf(EXPIRE_MINUTES), TimeUnit.MINUTES);
		
		return tokenObj;
	}

	@Override
	public JsonResponse logout(String token) {
		
		redisTemplate.delete(PREFIX + token);
		return ResUtils.success();
	}

	@Override
	public String getUserNo(String token) {
		
		String tokenObj = redisTemplate.opsForValue().get(PREFIX + token);
		
//		if(!StringUtil.isNotEmpty(tokenObj)){
//			throw new OwnException(ResEnum.ERROR_NO_LOGIN);
//		}
		OauthToken oauthToken = JSONObject.parseObject(tokenObj, OauthToken.class);
		return oauthToken.getUserNo() == null ? "" : oauthToken.getUserNo();
	}

	@Override
	public boolean hasUser(String token) {
		
		boolean flag = false;
		String tokenObj = redisTemplate.opsForValue().get(PREFIX + token);
		
		if(!StringUtil.isNotEmpty(tokenObj)){
			return flag;
		}
		OauthToken oauthToken = JSONObject.parseObject(tokenObj == null ? "" : tokenObj, OauthToken.class);

        String userNo = oauthToken.getUserNo() == null ? "" : oauthToken.getUserNo();
        
        if (StringUtil.isEmpty(userNo)) {
			return flag;
		}
		return !flag;
	}
}
