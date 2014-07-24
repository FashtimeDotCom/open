package com.jscn.open.web.message;


public enum ErrorMessage {

	SERVICE_QUERY_ERROR("服务查找失败"),
	SERVICE_NOT_EXIST("服务不存在"),
	SERVICE_NOT_AVAILABLE("服务状态不可用"),
	
	SIGN_EMPTY("签名为空"),
	SIGN_ERROR("签名校验错误"),
	
	PARTNER_EMPTY("partner参数为空"),
	PARTNER_NOT_EXIST("合作伙伴不存在"),
	PARTNER_STATUS_ERROR("合作伙伴未激活"),
	
	PERMITION_QUERY_ERROR("权限查询异常"),
	PERMITION_NOT_ERROR("没有权限"),
	PERMITION_IP_ERROR("客户IP 地址异常"),
	PERMITION_IP_LIMITED("客户IP 地址受限"),
	
	CHARSET_NOT_SUPPORT("编码格式不支持"),
	FORMART_NOT_SUPPORT("返回格式不支持"),
	
	SYSTEM_ERROR("系统异常");
	
	
	
	
	private String message;
	private ErrorMessage(String message){
		this.message = message;
	}
	
	
	public String getMessage(){
		return message;
	}
}
