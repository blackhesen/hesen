package com.hesen.biz.dao;

import java.util.List;

import com.hesen.model.User;
import com.hesen.page.Page;
import com.hesen.pojo.UserConditions;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
	User findByUserName(String username);
	
	/**
	 * 用户列表
	 * @return
	 */
	List<User> queryUser();

	int queryCount();
	
	/**
	 * 分页拦截器
	 * @param pages
	 * @return
	 */
	List<User> queryUserByPage(Page<User> pages);
	
	/**
	 * 批量添加
	 * @param conditions
	 */
	void batchInsert(List<UserConditions> conditions);
}