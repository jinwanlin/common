package com.jin.commons.exception;

import org.apache.commons.lang.StringUtils;

public enum CommonErrorInfo {

	成功("200","成功"),
	
	
	程序异常("500",""),
	
	码不能为空("581","码不能为空"),
	码错误("582","码错误"),
	码已过期("583","码已过期"),
	
	未获取到微信授权("590","未获取到微信授权"),
	openid不能为空("591","openid不能为空"),
	unionid不能为空("592","unionid不能为空"),
	
	
	
	未分类逻辑异常("10000","");
	
    private String code;
    private String desc;

    CommonErrorInfo() {
    }

    CommonErrorInfo(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CommonErrorInfo fromCode(String code) {
        CommonErrorInfo[] values = CommonErrorInfo.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].code.equals(code)) {
                return values[i];
            }
        }
        return null;
    }
    public static CommonErrorInfo fromCodeOrDesc(String value) {
    	if(StringUtils.isBlank(value)){
    		return null;
    	}
        CommonErrorInfo[] values = CommonErrorInfo.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].code)||value.equals(values[i].getDesc())) {
                return values[i];
            }
        }
        return null;
    }

}
