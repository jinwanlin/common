package com.qq.weixin.api.xcx;

import org.apache.log4j.Logger;

import com.jin.commons.exception.CommonException;
import com.jin.commons.httputil.HttpUtil;
import com.jin.commons.json.JsonUtil;

public class XcxUtil {
	private static Logger logger = Logger.getLogger(XcxUtil.class);

	/**
	 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
	 * 
	 * @param appid
	 * @param secrit
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static XcxOauth2Response oauth2(String appid, String secrit, String code) throws Exception {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secrit+"&js_code=" + code + "&grant_type=authorization_code";
		String result = HttpUtil.sendGet(url);
		logger.info("oauth2 result: "+result);
		XcxOauth2Response oauth2 = JsonUtil.json2Bean(result, XcxOauth2Response.class);
		if(oauth2.getErrcode()!=0) {
			throw new CommonException(oauth2.getErrmsg());
		}
		return oauth2;
	}
}
