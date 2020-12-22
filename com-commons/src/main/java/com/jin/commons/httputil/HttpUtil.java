package com.jin.commons.httputil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	private static final int DEFAULT_TIMEOUT = 8 * 1000;
	private static final HttpClientBuilder BUILDER = HttpClientBuilder.create();
	private static final HttpClient HTTP_CLIENT;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		BUILDER.setConnectionManager(cm);
		HTTP_CLIENT = BUILDER.build();
	}

	public static String sendGet(String url) {

		HttpGet httpGet = new HttpGet(url);
		logger.info(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		HttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		try {
			HttpResponse response = httpclient.execute(httpGet);
			return response2Str(response);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	public static String sendGet(String url, String cookie) {

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Cookie", cookie);
		logger.info(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		HttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		try {
			HttpResponse response = httpclient.execute(httpGet);
			return response2Str(response);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	private static String response2Str(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();

		InputStream responseContent = entity.getContent();

		BufferedReader reader = new BufferedReader(new InputStreamReader(responseContent, "utf-8"));
		StringBuffer sb = new StringBuffer();
		String b;
		while ((b = reader.readLine()) != null) {
			sb.append(b);
		}
		return sb.toString();
	}

	public static String post(String url, Map<String, String> params) throws Exception {
		HttpPost requestBase = new HttpPost(url);
		try {
			requestBase.setEntity(remakeParamMap(params));
			return request(url, requestBase);
		} catch (UnsupportedEncodingException e) {
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public static String postBody(String url, String data) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", " text/xml");
		InputStream is = new ByteArrayInputStream(data.getBytes());
		InputStreamEntity ise = new InputStreamEntity(is, data.getBytes().length);
		httpPost.setEntity(ise);
		try {
			RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
			HttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			// 访问https链接时使用自建的TrustManager
			// httpclient = WebClientDevWrapper.wrapClient(httpclient);
			HttpResponse response = httpclient.execute(httpPost);
			return response2Str(response);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return "";
	}

	private static String request(String url, HttpRequestBase requestBase) {
		Long l = System.currentTimeMillis();
		String responseResult = "";
		try {
			Long time = System.currentTimeMillis();
			HttpResponse result = execute(requestBase);
			logger.info("http use " + (System.currentTimeMillis() - time) + " mis");
			int statusCode = result.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				responseResult = EntityUtils.toString(result.getEntity());
			} else {
				logger.warn("request for url " + url + " error, statusCode=" + statusCode);
			}
		} catch (IOException e) {
			logger.error(e, e);
		}
		System.out.println("----use:" + (System.currentTimeMillis() - l));
		System.out.println(responseResult);
		System.out.println("----");
		return responseResult;
	}

	private static UrlEncodedFormEntity remakeParamMap(Map<String, String> params) throws UnsupportedEncodingException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8);
	}

	private static HttpResponse execute(HttpRequestBase requestBase) throws IOException, ClientProtocolException {
		return execute(requestBase, DEFAULT_TIMEOUT);
	}

	private static HttpResponse execute(HttpRequestBase requestBase, int timeOut)
			throws ClientProtocolException, IOException {
		requestBase.setConfig(timeOutConfig(timeOut));
		long time = System.currentTimeMillis();
		HttpResponse response = HTTP_CLIENT.execute(requestBase);
		logger.info("use " + (System.currentTimeMillis() - time) + "ms Execute HTTP on " + requestBase + ","
				+ requestBase.getConfig());
		return response;
	}

	private static RequestConfig timeOutConfig(int timeOut) {
		return RequestConfig.custom().setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut)
				.setConnectTimeout(timeOut).build();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		System.out.println(param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
			conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			conn.setRequestProperty("connection", "keep-alive");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
