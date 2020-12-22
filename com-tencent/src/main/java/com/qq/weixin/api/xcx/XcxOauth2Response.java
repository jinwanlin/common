package com.qq.weixin.api.xcx;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 小程序登录返回
 * 
 * @author jin
 *
 */
public class XcxOauth2Response {

	private String openid;

	/**
	 * 会话密钥
	 */
	@JsonProperty("session_key")
	private String sessionKey;

	private String unionid;

	private Integer errcode;

	private String errmsg;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
