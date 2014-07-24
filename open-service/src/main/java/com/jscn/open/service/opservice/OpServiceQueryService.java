package com.jscn.open.service.opservice;

import java.util.List;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.OpService;

public interface OpServiceQueryService {

	/**
	 * 根据服务名查找url
	 * @param serviceName
	 * @return
	 * @throws BusinessException
	 */
	public String getUrlByServiceName(String serviceName)
			throws BusinessException;

	public OpService getOpService(String serviceName, String methodName)
			throws BusinessException;

	public List<OpService> getOpServiceList(String serviceName,
			String methodName);

}