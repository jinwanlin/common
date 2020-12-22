package com.qq.weixin.api.gzh;

public class LoginResponseBean {

	private GzhUserInfo info;
	
	private GzhOauth2Response oauth2;
	
	

	public LoginResponseBean(GzhUserInfo info, GzhOauth2Response oauth2) {
		super();
		
		if(info==null) {
			info = new GzhUserInfo();
		}
		
		if(oauth2==null) {
			oauth2 = new GzhOauth2Response();
		}
		
		this.info = info;
		this.oauth2 = oauth2;
	}

	public GzhUserInfo getInfo() {
		return info;
	}

	public void setInfo(GzhUserInfo info) {
		this.info = info;
	}

	public GzhOauth2Response getOauth2() {
		return oauth2;
	}

	public void setOauth2(GzhOauth2Response oauth2) {
		this.oauth2 = oauth2;
	}
	
	
	
}
