package com.jscn.open.service.opservice;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.open.dao.OpService;
import com.jscn.open.dao.OpServiceExample;
import com.jscn.open.dao.OpServiceMapper;
import com.jscn.open.service.constant.StatusConstants;

@Service
public class OpServiceQueryServiceImpl implements OpServiceQueryService {

	@Autowired
	private OpServiceMapper opServiceMapper;
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceQueryService#getUrlByServiceName(java.lang.String)
	 */
	@Override
	public String getUrlByServiceName(String serviceName)throws BusinessException{
		
		OpServiceExample example = new OpServiceExample();
		OpServiceExample.Criteria criteria = example.createCriteria();
		criteria.andServiceNameEqualTo(serviceName);
		criteria.andStatusEqualTo(StatusConstants.SERVICE_STATUS_ON);
		
		List<OpService> list = opServiceMapper.selectByExample(example);
		if(list==null||list.size()==0){
			throw new BusinessException("根据serviceName查找url结果出错serviceName="+serviceName);
		}
		if(list.size()>1){
			throw new BusinessException("根据serviceName查找url结果大于1个serviceName="+serviceName);
		}
		if(StringUtils.isEmpty(list.get(0).getUrl())){
			throw new BusinessException("根据serviceName查找出的url为空serviceName="+serviceName);
		}
		return list.get(0).getUrl();
	}
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceQueryService#getOpService(java.lang.String, java.lang.String)
	 */
	@Override
	public OpService getOpService(String serviceName,String methodName) throws BusinessException{
		OpServiceExample example = new OpServiceExample();
		OpServiceExample.Criteria criteria = example.createCriteria();
		criteria.andServiceNameEqualTo(serviceName);
		if(!StringUtils.isEmpty(methodName)){
			criteria.andMethodNameEqualTo(methodName);
		}
		List<OpService> list = opServiceMapper.selectByExample(example);
		if(list==null||list.size()==0){
			return null;
		}
		if(list.size()>1){
			throw new BusinessException("查找结果大于1个serviceName="+serviceName+"."+(methodName==null?"":methodName));
		}
		return list.get(0);
	}
	
	
	/* (non-Javadoc)
	 * @see com.jscn.open.service.opservice.OpServiceQueryService#getOpServiceList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OpService> getOpServiceList(String serviceName,String methodName) {
		OpServiceExample example = new OpServiceExample();
		OpServiceExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(serviceName)){
			criteria.andServiceNameEqualTo(serviceName);
		}
		if(!StringUtils.isEmpty(methodName)){
			criteria.andMethodNameEqualTo(methodName);
		}
		return opServiceMapper.selectByExample(example);
		
	}
	
	
	
}
