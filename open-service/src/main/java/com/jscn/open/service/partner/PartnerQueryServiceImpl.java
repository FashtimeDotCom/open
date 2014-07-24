package com.jscn.open.service.partner;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.Partner;
import com.jscn.open.dao.PartnerExample;
import com.jscn.open.dao.PartnerMapper;

@Service
public class PartnerQueryServiceImpl implements PartnerQueryService {

	@Autowired
	private PartnerMapper partnerMapper;
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerQueryService#getPartner(java.lang.String)
	 */
	@Override
	public Partner getPartner(String partnerName)throws BusinessException{
		
		PartnerExample example = new PartnerExample();
		PartnerExample.Criteria criteria = example.createCriteria();
		criteria.andPartnerNameEqualTo(partnerName);
		List<Partner> list = partnerMapper.selectByExample(example);
		if(list==null||list.size()==0){
			return null;
		}
		if(list.size()>1){
			throw new BusinessException("根据partnerName查找partner结果大于1个partnerName="+partnerName);
		}
		
		return list.get(0);
		
	}
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerQueryService#getMd5key(java.lang.String)
	 */
	@Override
	public String getMd5key(String partnerName) throws BusinessException{
		Partner partner = getPartner(partnerName);
		if(partner==null){
			throw new BusinessException("partner不存在 partnerName="+partnerName);
		}
		return partner.getMd5Key();
	}
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerQueryService#getPartnerList(java.lang.String)
	 */
	@Override
	public List<Partner> getPartnerList(String partnerName){
		PartnerExample example = new PartnerExample();
		if(!StringUtils.isEmpty(partnerName)){
			PartnerExample.Criteria criteria = example.createCriteria();
			criteria.andPartnerNameEqualTo(partnerName);
		}
		return partnerMapper.selectByExample(example);
	}
}
