package com.jin.commons.exception;

/**
 * 自定义异常
 * 
 * @author jin
 *
 */
public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/** 错误消息 */
	private String msg;
	
	/** 错误代码 */
	private String code;

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}
	
	public CommonException(CommonErrorInfo ErrorInfo) {
		super(ErrorInfo.getDesc());
		this.msg = ErrorInfo.getDesc();
		this.code = ErrorInfo.getCode();
	}
	
	public CommonException(String msg) {
		super(msg);
		this.msg = msg;
		this.code = CommonErrorInfo.未分类逻辑异常.getCode();
	}
	
}
