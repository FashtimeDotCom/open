package com.jscn.open.service.partner;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.Partner;
import com.jscn.open.dao.PartnerMapper;
import com.jscn.open.service.opservicepartner.PermitManageService;

@Service
public class PartnerManageServiceImpl implements PartnerManageService {
	
	@Autowired
	private PartnerMapper partnerMapper;
	@Autowired
	private PartnerQueryService partnerQueryService;
	
	@Autowired
	private PermitManageService permitManageService;
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerManageService#addPartner(com.jscn.open.dao.Partner[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void addPartner(Partner[] partners) throws BusinessException{
		for(Partner partner : partners){
			if(StringUtils.isEmpty(partner.getPartnerName())){
				throw new BusinessException("合作伙伴名不能为空");
			}
			if(StringUtils.isEmpty(partner.getMd5Key())){
				throw new BusinessException("MD5 Key 不能为空");
			}
			if(StringUtils.isEmpty(partner.getStatus())){
				throw new BusinessException("状态 不能为空");
			}
			if(partnerQueryService.getPartner(partner.getPartnerName())!=null){
				throw new BusinessException("合作伙伴:"+partner.getPartnerName()+"已存在");
			}
			partner.setLastUpdateTime(new Date());
			partnerMapper.insert(partner);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerManageService#updatePartner(com.jscn.open.dao.Partner[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updatePartner(Partner[] partners) throws BusinessException{
		for(Partner partner : partners){
			if(StringUtils.isEmpty(partner.getPartnerName())){
				throw new BusinessException("合作伙伴名不能为空");
			}
			if(StringUtils.isEmpty(partner.getMd5Key())){
				throw new BusinessException("MD5 Key 不能为空");
			}
			if(StringUtils.isEmpty(partner.getStatus())){
				throw new BusinessException("状态 不能为空");
			}
			if(partner.getId()==null){
				throw new BusinessException("更新失败，ID不能为空");
			}
			List<Partner> existList = partnerQueryService.getPartnerList(partner.getPartnerName());
			if(existList.size()==1&&!existList.get(0).getId().equals(partner.getId())){
				throw new BusinessException("合作伙伴:"+partner.getPartnerName()+"已存在");
			}else if(existList.size()>1){
				throw new BusinessException("合作伙伴:"+partner.getPartnerName()+"已存在");
			}
			partner.setLastUpdateTime(new Date());
			partnerMapper.updateByPrimaryKey(partner);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.partner.PartnerManageService#deletePartner(com.jscn.open.dao.Partner[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deletePartner(Partner[] partners) throws BusinessException{
		for(Partner partner : partners){
			if(partner.getId()==null){
				throw new BusinessException("删除失败，ID不能为空");
			}
			partnerMapper.deleteByPrimaryKey(partner.getId());
			permitManageService.deleteByPartner(partner.getId());
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void savePartner(Partner[] addedPartner, Partner[] changedPartner)
			throws BusinessException {
		if(addedPartner!=null&&addedPartner.length>0){
			addPartner(addedPartner);
		}
		if(changedPartner!=null&&changedPartner.length>0){
			updatePartner(changedPartner);
		}
		
	}
	
	
}
