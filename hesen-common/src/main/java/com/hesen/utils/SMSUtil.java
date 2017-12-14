package com.hesen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 *
 * Created by hesen 2017/11/05
 */

public class SMSUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	
	private static final String TIME = "10000";
	
	private static final String POSITION = "cn-hangzhou";
	
	private static final String CONNECT = "sun.net.client.defaultConnectTimeout";
	
	private static final String READ = "sun.net.client.defaultReadTimeout";
	
	//初始化ascClient需要的几个参数
	private static final String PRODUCT = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	
	private static final String DOMAIN = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	
	private static final String ACCESS_KEY_ID = "LTAIjl45rfy3q3ZH";
	
	private static final String ACCESS_KEY_SECRET = "UpcuwqnG6QAGWOwwXeUQbtiOi0EKnb";
	
	private static final String SIGN = "Hesen男孩";//签名
	
	private static final String TEMPLATE_CODE = "SMS_109495028";//模板id
	
	public static boolean sendSMS(String mobile, String textBody){
		//设置超时时间-可自行调整
		System.setProperty(CONNECT, TIME);
		System.setProperty(READ, TIME);
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = null;
		try {
			profile = DefaultProfile.getProfile(POSITION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		
			DefaultProfile.addEndpoint(POSITION, POSITION, PRODUCT, DOMAIN);
		} catch (ClientException e1) {
			logger.error("服务器响应失败");
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
				
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(mobile);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(SIGN);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(TEMPLATE_CODE);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"name\":\""+textBody+"\"}");
		//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
				
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
				
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			logger.error("服务器响应失败");
		} catch (ClientException e) {
			logger.error("服务器响应失败");
		}
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			//请求成功
			logger.info("发送短信成功");
			return true;
		}
		return false;
	}
}
