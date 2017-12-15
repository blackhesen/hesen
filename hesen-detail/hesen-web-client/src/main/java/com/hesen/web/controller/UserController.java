package com.hesen.web.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hesen.dto.PageDTO;
import com.hesen.model.User;
import com.hesen.pojo.UserConditions;
import com.hesen.res.JsonObjectResponse;
import com.hesen.res.JsonResponse;
import com.hesen.service.UserService;

/**
 * Created by hesen on 2017-10-30
 */
@RestController
@RequestMapping(value = "/system/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/list")
	public JsonObjectResponse<PageDTO> list(){
		
		return userService.queryUserList();
	}
	
	/**
	 * MyBatis拦截器实现分页查询
	 * @return
	 */
	@PostMapping(value = "/list/page/intercept")
	public JsonObjectResponse<PageDTO> pageInterceptor(){
		
		return userService.queryUserListByPageInterceptor(1);
	}

	@GetMapping(value = "/export")
	public void export(HttpServletResponse response) throws Exception{
		
		//查询用户信息
		JsonObjectResponse<PageDTO> queryUserList = userService.queryUserList();
		List<User> userList = (List<User>)queryUserList.getData().getResult();
		
		XSSFWorkbook xwb = new XSSFWorkbook();//创建一个excel文档对象
		XSSFSheet sheet = xwb.createSheet("用户列表");//创建一个sheet对象
		
		//设置标题行
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("用户id");
		row.createCell(1).setCellValue("用户名");
		row.createCell(2).setCellValue("密码");
		
		for (int i = 1; i <= userList.size(); i++) {
			User user = userList.get(i-1);
			
			XSSFRow rows = sheet.createRow(i);
			rows.createCell(0).setCellValue(user.getId());
			rows.createCell(1).setCellValue(user.getUsername());
			rows.createCell(2).setCellValue(user.getPassword());
		}
		
		//设置相应规则
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(("用户列表" + ".xlsx").getBytes(), "iso-8859-1"));
		OutputStream outputStream = response.getOutputStream();
		xwb.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	@PostMapping(value = "/add")
	public JsonResponse add(List<UserConditions> conditions){
		
		return userService.addUser(conditions);
	}
}
