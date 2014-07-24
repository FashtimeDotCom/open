package com.jscn.open.web.valid;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.open.web.controller.RequestPO;
import com.jscn.open.web.message.ErrorMessage;

/**
 * 
 * 对于接入第三方应用进行验证
 * 
 * @author hexizheng
 * 
 */
@Component
public class Validator {

	@Autowired
	private SignValidator signValidor;
	@Autowired
	private ServiceValidator servicValidator;
	@Autowired
	private PermitionValidator permitionValidator;
	@Autowired
	private PartnerValidator partnerValidator;
	@Autowired
	private ParamValidator paramValidator;
	private  final Logger     logger = LoggerFactory.getLogger(getClass());

	/**
	 * 校验请求
	 * @param requestPO
	 * @param map
	 * @return
	 */
	public String valid(RequestPO requestPO,Map<String,String> map){
		String serviceName = requestPO.getSystem()+"_"+requestPO.getServiceName();
		String methodName = requestPO.getMethodName();
		String partner = requestPO.getPartner();
		String ip = requestPO.getIp();
		String sign = requestPO.getSign();
		try {
			paramValidator.checkSystemParam(requestPO,map);
			servicValidator.checkService(serviceName,methodName);
			if(servicValidator.isNeedValid(serviceName, methodName)){
				partnerValidator.checkPartner(partner);
				signValidor.checkSign(map,sign);
				permitionValidator.checkPermition(serviceName, methodName, partner,ip);
			}
		}catch (BusinessException e) {
			logger.error("校验失败：",e);
			return e.getMessage();
		}catch (SystemException e) {
			logger.error("校验失败：",e);
			return e.getMessage();
		}catch(Exception e){
			logger.error("校验失败：",e);
			return ErrorMessage.SYSTEM_ERROR.getMessage();
		}
		return null;
	}

}
