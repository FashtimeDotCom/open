package com.jscn.open.web.valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.Partner;
import com.jscn.open.service.constant.StatusConstants;
import com.jscn.open.service.partner.PartnerQueryService;
import com.jscn.open.web.message.ErrorMessage;

@Component
public class PartnerValidator {

	@Autowired
	private PartnerQueryService partnerQueryService;
	
	/**
	 * 检查第三方应用是否存在，及状态是否正常
	 * @param partner
	 */
	public void checkPartner(String partnerName)throws BusinessException{
		if(StringUtils.isEmpty(partnerName)){
			throw new BusinessException(ErrorMessage.PARTNER_EMPTY.getMessage());
		}
		Partner partner = partnerQueryService.getPartner(partnerName);
		if(partner==null){
			throw new BusinessException(ErrorMessage.PARTNER_NOT_EXIST.getMessage());
		}
		if(!StatusConstants.PARTNER_STATUS_ON.equals(partner.getStatus())){
			throw new BusinessException(ErrorMessage.PARTNER_STATUS_ERROR.getMessage());
		}
	}
	
	
}
