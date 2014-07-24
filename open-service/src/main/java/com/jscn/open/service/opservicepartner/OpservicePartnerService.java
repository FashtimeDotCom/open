package com.jscn.open.service.opservicepartner;

import java.util.List;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.PartnerService;

public interface OpservicePartnerService {

	public PartnerService getPartnerService(Long partnerId, Long serviceId)
			throws BusinessException;

	public List<PartnerService> getPermitList(Long partnerId);

}