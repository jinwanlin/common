package com.qq.weixin.api.gzh;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jin.commons.exception.CommonException;
import com.jin.commons.httputil.HttpUtil;
import com.jin.commons.json.JsonUtil;

public class GzhUtil {
	private static Logger logger = Logger.getLogger(GzhUtil.class);

	

	public static GzhUserInfo getUserInfo(String openid, String access_token) throws Exception {
		logger.info("getUserInfo(gzhUser, "+access_token+")");
		/**
		 已关注公众号
		 {
				"subscribe": 1,
				"openid": "oHvAGs9aUJ4hpBGJD_4HaKAm61pY",
				"nickname": "金万林",
				"sex": 1,
				"language": "zh_CN",
				"city": "",
				"province": "",
				"country": "中国",
				"headimgurl": "http:\/\/thirdwx.qlogo.cn\/mmopen\/ajNVdqHZLLDysibAngvv89SDqYPfXGPXItFbqWLwxB1CQibDG5ib55Duf9s3CO3C6q1ojfMD3YXgKmSEAJau4Xtew\/132",
				"subscribe_time": 1440299486,
				"unionid": "odio8tyN_Yvt6b13HLFaJyHtSsxs",
				"remark": "",
				"groupid": 100,
				"tagid_list": [100],
				"subscribe_scene": "ADD_SCENE_OTHERS",
				"qr_scene": 0,
				"qr_scene_str": ""
			}
			
			//没关注公众号
			{
				"openid": "oHvAGs9aUJ4hpBGJD_4HaKAm61pY",
				"nickname": "金万林",
				"sex": 1,
				"language": "zh_CN",
				"city": "",
				"province": "",
				"country": "中国",
				"headimgurl": "https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/DYAIOgq83erdNpvybH1K5W5toC4NW51IQvCsib3Jkuibfhor1SufoEUrSqbib4I7e5pYklBuFyDicLibib8a5If50ibibQ\/132",
				"privilege": [],
				"unionid": "odio8tyN_Yvt6b13HLFaJyHtSsxs"
			}
		 */
		String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		String userInfo = HttpUtil.sendGet(userInfoUrl);
		logger.info("userInfo:"+userInfo);
		
		GzhUserInfo info = JsonUtil.json2Bean(userInfo, GzhUserInfo.class);
		return info;
	}
	
	
	public static LoginResponseBean oauth2(String appid, String secrit, String code) throws Exception {
		/**
		 * 	{
		 * 		"access_token":"37_NEP8nMf7Jn-em7Vz4a0y55fsjY5FWgdwQrodd8pXOP_jTdeS5BvdQr9kZ69VE2W5QS7OZC02MYXNeCpF-tNKoAOYUwHjbsUkKDbS7PeRtyw",
		 * 		"refresh_token":"37_f5wUuGZyGAcJqsWaXf7FdZ90WDTPNsACVwkdwQbXGXrms75cz46-wp5G46QCJo4-ki6gx31Alo6HEC4M2N96XxpZiz6dNNOAsWcm73JejJQ",
		 * 		"expires_in":7200,
		 * 		"openid":"openidopenidopenidopenid",
		 * 		"unionid":"oHvAGs9aUJ4hpBGJD_4HaKAm61pY",
		 * 		"scope":"snsapi_base"
		 *	}
		 * }
		 */
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secrit+"&code="+code+"&grant_type=authorization_code";
		String result = HttpUtil.sendGet(url);
		logger.info("oauth2 result: "+result);
		
		
		GzhOauth2Response oauth2 = JsonUtil.json2Bean(result, GzhOauth2Response.class);
		
		if(StringUtils.isNotBlank(oauth2.getErrcode())) {
			throw new CommonException(oauth2.getErrmsg());
		}
		GzhUserInfo info = GzhUtil.getUserInfo(oauth2.getOpenid(), oauth2.getAccessToken());
		
		return new LoginResponseBean(info, oauth2);
		
		
	}

}
