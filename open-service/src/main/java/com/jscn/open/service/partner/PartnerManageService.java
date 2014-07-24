package com.jscn.open.service.partner;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.Partner;

public interface PartnerManageService {

	public void addPartner(Partner[] partners) throws BusinessException;

	public void updatePartner(Partner[] partners) throws BusinessException;

	public void deletePartner(Partner[] partners) throws BusinessException;

	/**
	 * 新增和更新
	 * @param addedPartner
	 * @param changedPartner
	 * @throws BusinessException
	 */
	public void savePartner(Partner[]addedPartner,Partner[]changedPartner)throws BusinessException;

}