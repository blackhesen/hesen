package com.hesen.web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hesen.enums.ResEnum;
import com.hesen.exception.OwnException;
import com.hesen.utils.QrUtil;

/**
 * Created by hesen on 2017-10-30
 */
@RestController
@RequestMapping(value = "/system/qr")
public class QrController {
	
	private Logger logger = LoggerFactory.getLogger(QrController.class);
	
	//生成二维码
	@GetMapping(value = "/generate")
	public void createQr(HttpServletResponse response){
		
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
//			QrUtil.createQrCode("金诚逸", outputStream);
			QrUtil.createQr("金诚逸", outputStream, "https://mallimg.easybao.com/FiBuF9YoFNUKukPtkePL2TQAalYQ");
			
		} catch (IOException e) {
			logger.error("生成二维码失败：{}", e.getMessage());
			throw new OwnException(ResEnum.SYSTEM_ERROR);
		}finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//解析二维码
	@PostMapping(value = "/resolve")
	public void resolveQr(HttpServletResponse response){
		
		ServletOutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			QrUtil.createQrCode("金诚逸", outputStream);
		} catch (IOException e) {
			logger.error("生成二维码失败：{}", e.getMessage());
			throw new OwnException(ResEnum.SYSTEM_ERROR);
		}
	}
}
