package com.jscn.open.service.opservicepartner;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jscn.open.dao.PartnerService;
import com.jscn.open.dao.PartnerServiceExample;
import com.jscn.open.dao.PartnerServiceMapper;

@Service
public class PermitManageServiceImpl implements PermitManageService {

	@Autowired
	private PartnerServiceMapper partnerServiceMapper;
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservicepartner.PermitManageService#savePermits(java.lang.Long, java.util.List)
	 */

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void savePermits(Long partnerId,List<Long> services) {
		deleteByPartner(partnerId);
		//添加新的
		for(Long serviceId: services){
			PartnerService partnerService = new PartnerService();
			partnerService.setPartnerId(partnerId);
			partnerService.setServiceId(serviceId);
			partnerService.setLastUpdateTime(new Date());
			partnerServiceMapper.insert(partnerService);
		}
	}

	@Override
	public void deleteByPartner(Long partnerId) {
		PartnerServiceExample example = new PartnerServiceExample();
		example.or().andPartnerIdEqualTo(partnerId);
		partnerServiceMapper.deleteByExample(example );
	}

	@Override
	public void deleteByService(Long serviceId) {
		PartnerServiceExample example = new PartnerServiceExample();
		example.or().andServiceIdEqualTo(serviceId);
		partnerServiceMapper.deleteByExample(example );
	}
}
