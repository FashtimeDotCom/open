package com.jscn.open.service.opservice;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.OpService;
import com.jscn.open.dao.OpServiceMapper;
import com.jscn.open.service.opservicepartner.PermitManageService;

@Service
public class OpServiceManageServiceImpl implements OpServiceManageService {

	@Autowired
	private OpServiceMapper opServiceMapper;
	@Autowired
	private OpServiceQueryService opServiceQueryService;
	
	@Autowired
	private PermitManageService permitManageService;
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceManageService#addService(com.jscn.open.dao.OpService[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void addService(OpService[] opServices) throws BusinessException{
		for(OpService opService : opServices){
			if(StringUtils.isEmpty(opService.getServiceName())){
				throw new BusinessException("服务名不能为空");
			}
			if(StringUtils.isEmpty(opService.getIsProtect())){
				throw new BusinessException("服务是否受保护 不能为空");
			}
			if(StringUtils.isEmpty(opService.getStatus())){
				throw new BusinessException("服务状态 不能为空");
			}
			if(opServiceQueryService.getOpService(opService.getServiceName(),opService.getMethodName())!=null){
				throw new BusinessException("服务"+opService.getServiceName()+"."+(opService.getMethodName()==null?"":opService.getMethodName())+"已存在");
			}
			opService.setLastUpdateTime(new Date());
			opServiceMapper.insert(opService);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceManageService#updateService(com.jscn.open.dao.OpService[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateService(OpService[] opServices) throws BusinessException{
		for(OpService opService : opServices){
			if(StringUtils.isEmpty(opService.getServiceName())){
				throw new BusinessException("服务名不能为空");
			}
			if(StringUtils.isEmpty(opService.getIsProtect())){
				throw new BusinessException("服务是否受保护 不能为空");
			}
			if(StringUtils.isEmpty(opService.getStatus())){
				throw new BusinessException("服务状态 不能为空");
			}
			if(opService.getId()==null){
				throw new BusinessException("更新失败，ID不能为空");
			}
			opService.setLastUpdateTime(new Date());
			
			OpService existService= opServiceQueryService.getOpService(opService.getServiceName(),opService.getMethodName());
			if(existService!=null&&!existService.getId().equals(opService.getId())){
				throw new BusinessException("服务"+opService.getServiceName()+"."+(opService.getMethodName()==null?"":opService.getMethodName())+"已存在");
			}
			opServiceMapper.updateByPrimaryKey(opService);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceManageService#deleteService(com.jscn.open.dao.OpService[])
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteService(OpService[] opServices) throws BusinessException{
		for(OpService opService : opServices){
			if(opService.getId()==null){
				throw new BusinessException("删除失败，ID不能为空");
			}
			opServiceMapper.deleteByPrimaryKey(opService.getId());
			//删除权限
			permitManageService.deleteByService(opService.getId());
		}
	}

	@Override
	public void saveService(OpService[] changedService, OpService[] addService)
			throws BusinessException {
		
		if(addService!=null&&addService.length>0){
			addService(addService);
		}
		if( changedService!=null && changedService.length > 0){
			updateService(changedService);
		}
	}
}
