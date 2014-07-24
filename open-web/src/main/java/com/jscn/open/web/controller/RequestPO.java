package com.jscn.open.web.controller;

import com.jscn.commons.core.utils.StringUtils;
import com.jscn.open.web.constant.RequestConstant;

public class RequestPO {
	
	//合作伙伴名称
	private String partner;
	
	//签名
	private String sign;
	
	
	private String system;
	private String serviceName;
	private String methodName;
	
	private String ip;
	
	private String inputCharset;
	
	//格式，xml 或 json
	private String formart;
	
    public String getInputCharset() {
        if(StringUtils.isEmpty(inputCharset)){
           return RequestConstant.UTF_8;
        }
        return inputCharset;
    }
    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

	public String getSystem() {
		return system;
	}
	
	



    public void setSystem(String system) {
		this.system = system;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getFormart() {
		return formart;
	}

	public void setFormart(String formart) {
		this.formart = formart;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
