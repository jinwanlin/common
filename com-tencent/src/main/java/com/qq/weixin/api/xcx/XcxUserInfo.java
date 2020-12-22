package com.qq.weixin.api.xcx;

/**
 * 小程序提交的用户信息json转成这个bean
 * @author jin
 *
 */
public class XcxUserInfo {

	private String openid;
	private String nickName;
	private String avatarUrl;
	private String gender;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
