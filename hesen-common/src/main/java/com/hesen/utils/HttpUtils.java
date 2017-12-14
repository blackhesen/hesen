package com.hesen.utils;
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public HttpUtils() {
    }

    public static String postJSON(String url, Map<String, Object> args) throws IOException {
        logger.info("发送的url:" + url + ";发送的数据是:" + JSON.toJSONString(args));
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json; charset=utf-8");
        httppost.setEntity(new StringEntity(JSON.toJSONString(args), "utf8"));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static String get(String url, String param) throws IOException {
        String result = "";
        BufferedReader in = null;

        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            Iterator var8 = map.keySet().iterator();

            while(var8.hasNext()) {
                String key = (String)var8.next();
                System.out.println(key + "--->" + map.get(key));
            }

            String line;
            for(in = new BufferedReader(new InputStreamReader(connection.getInputStream())); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var18) {
            System.out.println("发送GET请求出现异常！" + var18);
            var18.printStackTrace();
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (Exception var17) {
                var17.printStackTrace();
            }

        }

        return result;
    }

    public static String postBody(String url, String body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(75000).setConnectTimeout(45000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = null;
            String result = null;
            try {
                if (body == null) {
                    // pass
                } else {
                    StringEntity entity = new StringEntity(body, "UTF-8");
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                    httpPost.setEntity(entity);
                }
                response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                logger.info(String.format("http protocol:%s statusCode:%s reasonPhrase:%s", new Object[]{statusLine.getProtocolVersion(), statusLine.getStatusCode(), statusLine.getReasonPhrase()}));
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, Consts.UTF_8);
            } catch (UnsupportedEncodingException e) {
                logger.info("httpClient post request error");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    logger.info("httpClient post request error");
                }
            }
            logger.info(String.format("post request url is %s, body is %s, response data is \n%s", url, body, result));
            return result == null ? "" : result.trim();

        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.info("httpClient post request error", e);
            }
        }
    }
    
    /**
     * 获取网络内容输入流对象
     * @param url
     * @param imgPath
     * @return
     */
    public static InputStream post(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpPost = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(75000).setConnectTimeout(45000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = null;
            try {
				response = httpClient.execute(httpPost);
				InputStream content = response.getEntity().getContent();
				return content;
			} catch (IOException e) {
				e.printStackTrace();
			}

        } catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }
}

