package com.qq.weixin.api.gzh;

import java.util.List;

/**
 * 公众号接口 获取用户信息
 * 
 * @author jin
 *
 */

public class GzhUserInfo {
	
	/**
	 * 没有允许获得头像
	 * {"errcode":48001,"errmsg":"api unauthorized, hints: [ req_id: FkLFAawgE-g5ZpTA ]"}
	 */
	private String errcode;
	
	private String errmsg;
	
	/**
	 * 用户 是1 否0 订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	private Integer subscribe;

	private String openid;
	
	private String nickname;
	
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private Integer sex;
	
	private String language;
	
	private String city;
	
	private String province;
	
	private String country;
	
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	private String headimgurl;
	
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private Long subscribe_time;
	
	private List<Object> privilege;
	
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 */
	private String unionid;
	
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	private String remark;
	
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）
	 */
	private Integer groupid;
	
	/**
	 * 用户被打上的标签ID列表
	 */
	private List<Long> tagid_list;
	
	/**
	 * 返回用户关注的渠道来源，
	 * ADD_SCENE_SEARCH 公众号搜索，
	 * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
	 * ADD_SCENE_PROFILE_CARD 名片分享，
	 * ADD_SCENE_QR_CODE 扫描二维码，
	 * ADD_SCENE_PROFILE_LINK 图文页内名称点击，
	 * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
	 * ADD_SCENE_PAID 支付后关注，
	 * ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，
	 * ADD_SCENE_OTHERS 其他
	 */
	private String subscribe_scene;
	
	/**
	 * 二维码扫码场景（开发者自定义）
	 */
	private Integer qr_scene;
	
	/**
	 * 二维码扫码场景描述（开发者自定义）
	 */
	private String qr_scene_str;
	
	

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return sex;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setPrivilege(List<Object> privilege) {
		this.privilege = privilege;
	}

	public List<Object> getPrivilege() {
		return privilege;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getUnionid() {
		return unionid;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public Long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public List<Long> getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(List<Long> tagid_list) {
		this.tagid_list = tagid_list;
	}

	public String getSubscribe_scene() {
		return subscribe_scene;
	}

	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}

	public Integer getQr_scene() {
		return qr_scene;
	}

	public void setQr_scene(Integer qr_scene) {
		this.qr_scene = qr_scene;
	}

	public String getQr_scene_str() {
		return qr_scene_str;
	}

	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}
	
	
}
