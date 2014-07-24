package com.jscn.open.web.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.open.dao.OpService;
import com.jscn.open.service.constant.StatusConstants;
import com.jscn.open.service.opservice.OpServiceQueryService;
import com.jscn.open.web.message.ErrorMessage;

@Component
public class ServiceValidator {

	@Autowired
	private OpServiceQueryService opServiceQueryService;
	
	/**
	 * 检查服务是否存在及是否处于可用状态
	 */
	public void checkService(String serviceName,String methodName)throws BusinessException{
		OpService opService = getOpservice(serviceName,methodName);
		if(opService==null){
			throw new BusinessException(ErrorMessage.SERVICE_NOT_EXIST.getMessage());

		}
		if(!StatusConstants.SERVICE_STATUS_ON.equals(opService.getStatus())){
			throw new BusinessException(ErrorMessage.SERVICE_NOT_AVAILABLE.getMessage());
		}
	}
	
	/**
	 * 是否需要校验
	 * @param serviceName
	 * @param methodName
	 * @return
	 */
	public boolean isNeedValid(String serviceName,String methodName){
		OpService opService = getOpservice(serviceName,methodName);
		if(StatusConstants.SERVICE_IS_PROTECTED.equals(opService.getIsProtect())){
			return true;
		}else{
			return false;
		}
	}
	
	private OpService getOpservice(String serviceName,String methodName){
		try {
			return opServiceQueryService.getOpService(serviceName, methodName);
		} catch (BusinessException e) {
			throw new SystemException(ErrorMessage.SERVICE_QUERY_ERROR.getMessage(),e);
		}
	}
}
