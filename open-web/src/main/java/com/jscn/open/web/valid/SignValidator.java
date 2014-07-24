package com.jscn.open.web.valid;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.utils.EncryptUtil;
import com.jscn.open.service.partner.PartnerQueryService;
import com.jscn.open.web.constant.RequestConstant;
import com.jscn.open.web.message.ErrorMessage;


/**
 * 签名验证
 * @author hexizheng
 *
 */

@Component
public class SignValidator {
	
	@Autowired
	private PartnerQueryService partnerQueryService;
	
	private  final Logger     logger = LoggerFactory.getLogger(getClass());

	/**
	 * 验证签名
	 * @param requst
	 * @return
	 */
	public void checkSign(Map<String,String> map,String sign ) throws BusinessException{
		if(StringUtils.isEmpty(sign)){
			throw new  BusinessException(ErrorMessage.SIGN_EMPTY.getMessage());
		}
		if(!sign.equals(getSign(map))){
			logger.error("签名不一致，传入sign=[{}],系统计算值sign=[{}]",sign,getSign(map));
			throw new  BusinessException(ErrorMessage.SIGN_ERROR.getMessage());

		}
	}
	
	
	private String getSign(Map<String,String> map) throws BusinessException{
	    //按照键名称字母升序排序
	    map = new TreeMap<String,String>(map);
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			sb.append(map.get(iterator.next())+"|");
		}
		sb.append(partnerQueryService.getMd5key((String)map.get(RequestConstant.PARTNER)));
		String charset = map.get(RequestConstant.INPUT_CHARSET);
		if(StringUtils.isEmpty(charset)){
			charset = RequestConstant.UTF_8;
		}
		return EncryptUtil.md5Hex(sb.toString(),charset);
	}
}
