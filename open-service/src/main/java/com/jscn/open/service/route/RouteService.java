package com.jscn.open.service.route;

import java.util.Map;

import com.jscn.commons.core.exceptions.BusinessException;

public interface RouteService {

	/**
	 * 根据服务名和方法名调用相应方法
	 * @param beanName 服务名
	 * @param methodName 方法名
	 * @param map 参数
	 * @return
	 * @throws BusinessException
	 */
	public String excute(String beanName, String methodName,String formart,
			Map<String, String> map) throws BusinessException;

}