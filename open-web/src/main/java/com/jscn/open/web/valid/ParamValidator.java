package com.jscn.open.web.valid;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.utils.StringUtils;
import com.jscn.open.web.constant.RequestConstant;
import com.jscn.open.web.controller.RequestPO;
import com.jscn.open.web.message.ErrorMessage;

@Component
public class ParamValidator {
	
	public void checkSystemParam(RequestPO requestPO,Map<String,String> map)throws BusinessException{
		String inputCharset = map.get(RequestConstant.INPUT_CHARSET);
		checkCharset(inputCharset);
		
		String formart = requestPO.getFormart();
		checkFormart(formart);
		
	}


	private void checkCharset(String inputCharset) throws BusinessException {
		if(!StringUtils.isEmpty(inputCharset)){
			if(!RequestConstant.GBK.equalsIgnoreCase(inputCharset)&&!RequestConstant.UTF_8.equalsIgnoreCase(inputCharset)){
				throw new BusinessException(ErrorMessage.CHARSET_NOT_SUPPORT.getMessage()+":"+inputCharset);
			}
		}
	}
	
	private void checkFormart(String formart) throws BusinessException {
		if(!StringUtils.isEmpty(formart)){
			if(!RequestConstant.XML.equalsIgnoreCase(formart)&&!RequestConstant.JSON.equalsIgnoreCase(formart)){
				throw new BusinessException(ErrorMessage.FORMART_NOT_SUPPORT.getMessage()+":"+formart);
			}
		}
	}
}
