package com.jscn.open.service.partner;

import java.util.List;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.Partner;

public interface PartnerQueryService {

	public Partner getPartner(String partnerName) throws BusinessException;

	/**
	 * 取得md5key
	 * @param partnerId
	 * @return
	 * @throws BusinessException
	 */
	public String getMd5key(String partnerName) throws BusinessException;

	public List<Partner> getPartnerList(String partnerName);

}