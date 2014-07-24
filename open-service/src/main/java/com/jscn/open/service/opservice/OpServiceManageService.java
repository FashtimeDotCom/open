package com.jscn.open.service.opservice;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.OpService;

public interface OpServiceManageService {

	public void addService(OpService[] opServices) throws BusinessException;

	public void updateService(OpService[] opServices) throws BusinessException;

	public void deleteService(OpService[] opServices) throws BusinessException;
	
	/**
	 * 新增和更新 服务
	 * @param changedService
	 * @param addService
	 * @throws BusinessException
	 */
	public void saveService(OpService[] changedService,OpService[] addService) throws BusinessException;

}
