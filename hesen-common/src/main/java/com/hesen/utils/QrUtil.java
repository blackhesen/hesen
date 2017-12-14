package com.hesen.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import sun.misc.BASE64Encoder;

/**
 * Created by hesen on 2017-12-12
 */
public class QrUtil {
	
	private static Logger logger = LoggerFactory.getLogger(QrUtil.class);
	
	//定义二维码的样式
	private static final int WIDTH = 300; //宽度
	private static final int HEIGHT = 300; //高度
	private static final String  FORMAT = "png"; //图片格式化
	private static final int BLACK = 0xFF000000; //黑色
	private static final int WHITE = 0xFFFFFFFF; //蓝色
	
	/**
	 * 创建二维码写入流
	 * @param contents
	 * @param stream
	 * @return
	 */
	public static void createQrCode(String contents, OutputStream outputStream){
		
		try {
			BitMatrix matrix = buildBitMatrix(contents);
			BufferedImage image = toBufferedImage(matrix);
			ImageIO.write(image, FORMAT, outputStream);
		} catch (Exception e) {
			logger.error("二维码写入流失败：{}", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成BASE64Encoder加密的二维码字符串
	 * @param contents 包含内容
	 * @param logo	logo
	 * @return
	 */
	public static String createQrCodeWithLogo(String contents, Image logo){
		try {
            BASE64Encoder encoder   = new BASE64Encoder();
            BitMatrix     bitMatrix = buildBitMatrix(contents);

            BufferedImage         image        = toBufferedImage(bitMatrix);
            Graphics2D            gs           = image.createGraphics();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int widthLogo  = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null);
            int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getWidth(null);

            //logo放中间
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            gs.drawImage(logo, x, y, widthLogo, heightLogo, null);

            gs.dispose();
            logo.flush();
            ImageIO.write(image, FORMAT, outputStream);
            byte[] bytes = outputStream.toByteArray();

            return "data:image/png;base64," + encoder.encode(bytes).replace("\n", "").trim();
        } catch (Exception e) {
        	logger.error("创建二维码失败：{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 生成带logo的二维码
	 * @param contents 包含内容
	 * @param logo	logo
	 * @return
	 */
	public static void createQrCodeWithLogo(String contents, Image logo, OutputStream outputStream){
		try {
            BitMatrix             bitMatrix    = buildBitMatrix(contents);
            BufferedImage         image        = toBufferedImage(bitMatrix);
            Graphics2D            gs           = image.createGraphics();

            int widthLogo  = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null);
            int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getWidth(null);

            //logo放中间
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            gs.drawImage(logo, x, y, widthLogo, heightLogo, null);

            gs.dispose();
            logo.flush();
            ImageIO.write(image, FORMAT, outputStream);
        } catch (Exception e) {
        	logger.error("创建二维码失败：{}", e.getMessage());
            e.printStackTrace();
        }
	}
	/**
	 * 生成二维码(如果imgPath为null或空string，生成普通二维码，否则，生成带logo的二维码 )
	 * @param contents
	 * @param stream
	 * @param imgPath	if imgPath is null or empty, generate QrCode without Logo, otherwise, generate QrCode with Logo
	 */
	public static void createQr(String contents, OutputStream outputStream, String imgPath){
		try {
		if (StringUtils.isEmpty(imgPath)) {
			createQrCode(contents, outputStream);
			return;
		}
		//插入logo
		InputStream inputStream = HttpUtils.post(imgPath);
		
		ByteArrayOutputStream oArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		
		int len = 0;
		while((len = inputStream.read(buffer)) != -1){
			oArrayOutputStream.write(buffer,0,len);
		}
		File  file = new File("D:/jj.jpg");
		byte[] data = oArrayOutputStream.toByteArray();
		
		FileOutputStream out = new FileOutputStream(file);
		
		out.write(data);
		
		Image logo = ImageIO.read(file);
			createQrCodeWithLogo(contents, logo, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化参数及创建矩阵
	 * @return
	 */
	private static BitMatrix buildBitMatrix(String contents){
		try {
			HashMap<EncodeHintType, Object> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN, 2);
			return new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
		} catch (WriterException e) {
			logger.error("二维码初始化参数失败：{}", e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 绘制二维码
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix){
		
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				image.setRGB(x, y, matrix.get(x, y) ? BLACK :WHITE);
			}
		}
		return image;
	}
}
