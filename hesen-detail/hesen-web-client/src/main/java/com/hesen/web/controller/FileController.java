package com.hesen.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hesen.enums.ResEnum;
import com.hesen.res.JsonObjectResponse;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云服务接口
 * Created by hesen on 2017-11-23
 */
@RestController
@RequestMapping(value = "/api/system/file")
public class FileController {
	
	private static Logger LOG = LoggerFactory.getLogger(FileController.class);
	
	@Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;
	
    /**
     * 获取上传令牌>>>>>>为了客户端上传提供上传凭证
     *
     * @return
     */
    @RequestMapping(value = "/uptoken", method = RequestMethod.POST)
    public JsonObjectResponse uptoken() {
        LOG.info("accessKey {}, secretKey {}, bucket {} ", accessKey, secretKey, bucket);
        JsonObjectResponse response = new JsonObjectResponse();
        long expireSeconds = 36000;
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", upToken);
        response.setCode(ResEnum.RES_CODE_SUCCESS.getCode());
        response.setErrmsg(ResEnum.RES_CODE_SUCCESS.getErrmsg());
        response.setData(tokenMap);
        return response;
    }
}
