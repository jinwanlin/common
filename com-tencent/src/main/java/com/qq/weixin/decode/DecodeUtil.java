package com.qq.weixin.decode;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class DecodeUtil {

	private static Logger logger = Logger.getLogger(DecodeUtil.class);
	
	/**
	 * 用户授权昵称头像后  解密小程序unionid
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String decodeXcxUnionid(String sessionKey, String encryptedData, String iv) throws Exception {
		
		logger.info("sessionKey:"+sessionKey);
		
		String result = WechatDecryptDataUtil.decryptData(encryptedData, sessionKey, iv);
		logger.info("decode userInfo result:"+result);
//		{
//			"openId": "oIMkF5vSKLDDpzVSTYO9CcBvvT6o",
//			"nickName": "呦呦",
//			"gender": 0,
		
//			"language": "zh_CN",
//			"city": "",
//			"province": "",
//			"country": "",
		
//			"avatarUrl": "https://thirdwx.qlogo.cn/mmopen/vi_32/vU88UyCnNwjdsZ0XbtvxTTQBYtvQ3cYhlXlOKOEjriarys2pPNf7iafSDalQXD6znyFib9WiaWODZXysc3LjFeib1Og/132",
//			"unionId": "odio8t0hPayr9JXX5NqBG-y_OjqY", //注意属性名I是大写
		
//			"watermark": {
//				"timestamp": 1608972806,
//				"appid": "wxd4b918a5a0d34b8c"
//			}
//		}
		
		if(StringUtils.isNotBlank(result)) {
			if(result.startsWith("{")) {
				JSONObject json = JSONObject.fromObject(result);
				if(json.containsKey("unionId")) {//注意I是大写
					String unionid = json.getString("unionId");//注意I是大写
					logger.info("decodeUnionid unionid:"+unionid);
					return unionid;
				}
			}
		}
		
		return null;
	}
	




	/**
	 * 小程序解密手机号
	 * @param sessionKey
	 * @param encryptedData
	 * @param iv
	 * @return
	 */
	public static String decodeXcxPhoneNum(String sessionKey, String encryptedData, String iv) {
		logger.info("sessionKey:"+sessionKey);
		
//		{
//			"phoneNumber": "15810845422",
//			"purePhoneNumber": "15810845422",
//			"countryCode": "86",
//			"watermark": {
//				"timestamp": 1592646145,
//				"appid": "wx5af03ce54dbe6401"
//			}
//		}
		String result = WechatDecryptDataUtil.decryptData(encryptedData, sessionKey, iv);
		logger.info("decode phoneNum result:"+result);
		if(result!=null) {
			if(result.startsWith("{")) {
				JSONObject json = JSONObject.fromObject(result);
				if(json.containsKey("phoneNumber")) {
					String phoneNum = json.getString("phoneNumber");
					return phoneNum;
				}
			}
		}
		return null;
	}
}
