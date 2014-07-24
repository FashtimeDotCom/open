package com.jscn.open.service.opservicepartner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.PartnerService;
import com.jscn.open.dao.PartnerServiceExample;
import com.jscn.open.dao.PartnerServiceMapper;

@Service
public class OpservicePartnerServiceImpl implements OpservicePartnerService {

	@Autowired
	private PartnerServiceMapper partnerServiceMapper;
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservicepartner.OpservicePartnerService#getPartnerService(java.lang.Long, java.lang.Long)
	 */
	@Override
	public PartnerService getPartnerService(Long partnerId,Long serviceId) throws BusinessException{
		
		PartnerServiceExample example = new PartnerServiceExample();
		PartnerServiceExample.Criteria criteria = example.createCriteria();
		criteria.andPartnerIdEqualTo(partnerId);
		criteria.andServiceIdEqualTo(serviceId);
		List<PartnerService> list = partnerServiceMapper.selectByExample(example);
		
		if(list==null||list.size()==0){
			return null;
		}
		if(list.size()>1){
			throw new BusinessException("查找结果集多于一个 partnerId="+partnerId+"serviceId="+serviceId);
		}
		return list.get(0);
	}
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservicepartner.OpservicePartnerService#getPermitList(java.lang.Long)
	 */
	@Override
	public List<PartnerService> getPermitList(Long partnerId){
		PartnerServiceExample example = new PartnerServiceExample();
		if(partnerId!=null){
			PartnerServiceExample.Criteria criteria = example.createCriteria();
			criteria.andPartnerIdEqualTo(partnerId);
		}
		return  partnerServiceMapper.selectByExample(example);
		
	}
}
