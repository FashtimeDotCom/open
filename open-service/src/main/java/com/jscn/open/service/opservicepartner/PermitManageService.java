package com.jscn.open.service.opservicepartner;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jscn.commons.core.exceptions.BusinessException;

public interface PermitManageService {

	/**
	 * 保存权限，先 删除特定合作伙伴所有权限，然后在添加，事务
	 * @param partnerId
	 * @param serviceId
	 * @throws BusinessException 
	 */

	
	public void savePermits(Long partnerId, List<Long> services)
			throws BusinessException;
	/**
	 * 删除合作伙伴所有权限
	 * @param partnerId
	 */
	public void deleteByPartner(Long partnerId);
	/**
	 * 删除合作伙伴所有权限
	 * @param partnerId
	 */
	public void deleteByService(Long serviceId);
}