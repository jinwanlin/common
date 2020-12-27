package com.qq.weixin.api.erweima;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jin.commons.exception.CommonException;
import com.jin.commons.httputil.HttpUtil;
import com.jin.commons.json.JsonUtil;
import com.qq.weixin.api.xcx.XcxOauth2Response;

public class XcxErweimaUtil {

	
	private static Logger logger = Logger.getLogger(XcxErweimaUtil.class);

	/**
	 * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
	 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.get.html
	 * @param path 扫码进入的小程序页面路径，最大长度 128 字节，不能为空；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"，即可在 wx.getLaunchOptionsSync 接口中的 query 参数获取到 {foo:"bar"}。
	 * @param accessToken
	 * @throws Exception
	 */
	public static void a_wxacodeGet(String path, Map<String, String> params, String accessToken) throws Exception {
		String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
		
		String result = HttpUtil.post(url, params);
		logger.info("oauth2 result: "+result);
		XcxOauth2Response oauth2 = JsonUtil.json2Bean(result, XcxOauth2Response.class);
		if(oauth2.getErrcode()!=0) {
			throw new CommonException(oauth2.getErrmsg());
		}
	}
	
	
	/**
	 * 接口 B：适用于需要的码数量极多的业务场景
	 * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。
	 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
	 * @param page 必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
	 * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
	 * @param accessToken
	 * @throws Exception
	 */
	public static void b_wxacodeGetUnlimited(String page, String scene, String accessToken) throws Exception {
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", page);
		params.put("scene", scene);
		String result = HttpUtil.post(url, params);
		logger.info("oauth2 result: "+result);
		XcxOauth2Response oauth2 = JsonUtil.json2Bean(result, XcxOauth2Response.class);
		if(oauth2.getErrcode()!=0) {
			throw new CommonException(oauth2.getErrmsg());
		}
	}
	
	

	public static void main(String[] args) throws Exception {
		String accessToken = "40_6QyPxICyDAtpuo6eX-jQhvITumBEU9HDcj1Tm5X20xDYm7dhh0VvC0VmDk2SnxkgIDqCvAMpxvp9hUdWJ74aPWQ-wxXw2Kk75C9nXejysk-axUxlP4i6sTExA68sjl05SSbR3wYk_x0-E032AXWhACAEBA";
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopId", "1");
		String path = "pages/shop/shaomaQuhao/index";
		a_wxacodeGet(path, params, accessToken);		
		
	}
}
