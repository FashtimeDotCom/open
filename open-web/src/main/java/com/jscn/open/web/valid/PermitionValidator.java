package com.jscn.open.web.valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.OpService;
import com.jscn.open.dao.Partner;
import com.jscn.open.dao.PartnerService;
import com.jscn.open.service.constant.StatusConstants;
import com.jscn.open.service.opservice.OpServiceQueryService;
import com.jscn.open.service.opservicepartner.OpservicePartnerService;
import com.jscn.open.service.partner.PartnerQueryService;
import com.jscn.open.web.message.ErrorMessage;

@Component
public class PermitionValidator {

	@Autowired
	private OpServiceQueryService opServiceQueryService;
	
	@Autowired
	private OpservicePartnerService opservicePartnerService;
	
	@Autowired
	private PartnerQueryService partnerQueryService;
	
	/**
	 * 检查应用服务权限
	 * @param serviceName
	 * @param methodName
	 * @param partnerId
	 * @param ip
	 * @throws BusinessException
	 */
	public void checkPermition(String serviceName,String methodName,String partnerName,String ip)throws BusinessException{
		
		Partner partner = partnerQueryService.getPartner(partnerName);
		
		OpService opService = opServiceQueryService.getOpService(serviceName, methodName);
		PartnerService partnerService = null;
		try {
			partnerService = opservicePartnerService.getPartnerService(partner.getId(), opService.getId());
		} catch (BusinessException e) {
			throw new BusinessException(ErrorMessage.PERMITION_QUERY_ERROR.getMessage(),e);
		}
		if(partnerService==null){
			throw new BusinessException(ErrorMessage.PERMITION_NOT_ERROR.getMessage());
		}
		if(!StringUtils.isEmpty(partner.getIpList())){
			checkIp(ip,partner.getIpList());
		}
	}
	
	/**
	 * 检查ip是否受限，没有配置ip列表不检查
	 * @param ip
	 * @param ipList
	 * @throws BusinessException
	 */
	private void checkIp(String ip,String ipList)throws BusinessException{
		if(StringUtils.isEmpty(ip)){
			throw new BusinessException(ErrorMessage.PERMITION_IP_ERROR.getMessage());
		}
		boolean isIn = false;
		String[] ipArray = ipList.split(",");
		for(String limitIp : ipArray){
			if(ip.equals(limitIp)){
				isIn = true;
			}
		}
		if(!isIn){
			throw new BusinessException(ErrorMessage.PERMITION_IP_LIMITED.getMessage());
		}
	}
	
}
