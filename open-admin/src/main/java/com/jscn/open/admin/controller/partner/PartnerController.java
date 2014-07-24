package com.jscn.open.admin.controller.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.utils.JsonUtil;
import com.jscn.open.admin.utils.JsonData;
import com.jscn.open.dao.Partner;
import com.jscn.open.service.partner.PartnerManageService;
import com.jscn.open.service.partner.PartnerQueryService;

@Controller
@RequestMapping("partner")
public class PartnerController {
	@Autowired
	private PartnerQueryService partnerQueryService;
	@Autowired
	private PartnerManageService partnerManageService;
	
	private  final Logger     logger = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping("list")
	public String list(ModelMap model){
		
		List<Partner> list = partnerQueryService.getPartnerList("");
		model.addAttribute("partner",JsonData.toJson(list));
		
		return "partner/list";
	}
	
	@RequestMapping("save")
	public @ResponseBody Map<String,String> save(String addedPartners,String changedPartners){
		Map<String,String> retMap = new HashMap<String,String>();
		
		Partner[] addedPartner = JsonUtil.json2Object(addedPartners, Partner[].class);
		Partner[] changedPartner = JsonUtil.json2Object(changedPartners, Partner[].class);
		try {
			partnerManageService.savePartner(addedPartner,changedPartner);
			retMap.put("message","保存成功");
			retMap.put("refresh","true");
		} catch (BusinessException e) {
			logger.error("保存合作伙伴出错",e);
			retMap.put("message",e.getMessage());
		}catch (Exception e) {
			logger.error("保存合作伙伴出错",e);
			retMap.put("message","系统异常");
		}

		return retMap;
	}
	@RequestMapping("delete")
	public @ResponseBody Map<String,String> delete(String partnerStr){
		Map<String,String> retMap = new HashMap<String,String>();
		Partner[] partners = JsonUtil.json2Object(partnerStr, Partner[].class);
		if(partners!=null&&partners.length>0){
			try {
				partnerManageService.deletePartner(partners);
				retMap.put("message","删除成功");
			} catch (BusinessException e) {
				logger.error("删除合作伙伴出错",e);
				retMap.put("message",e.getMessage());
			}catch (Exception e) {
				logger.error("删除合作伙伴出错",e);
				retMap.put("message","系统异常");
			}
		}else{
			retMap.put("message","请选择需要删除的数据");
		}
		retMap.put("refresh","true");
		return retMap;
	}
}
