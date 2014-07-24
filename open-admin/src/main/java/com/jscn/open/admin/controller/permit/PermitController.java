package com.jscn.open.admin.controller.permit;

import java.util.ArrayList;
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

import com.jscn.commons.core.utils.StringUtils;
import com.jscn.open.admin.utils.JsonData;
import com.jscn.open.dao.OpService;
import com.jscn.open.dao.Partner;
import com.jscn.open.dao.PartnerService;
import com.jscn.open.service.opservice.OpServiceQueryService;
import com.jscn.open.service.opservicepartner.OpservicePartnerService;
import com.jscn.open.service.opservicepartner.PermitManageService;
import com.jscn.open.service.partner.PartnerQueryService;

@Controller
@RequestMapping("permit")
public class PermitController {
	
	@Autowired
	private PartnerQueryService partnerQueryService;
	@Autowired
	private OpservicePartnerService permitService;
	@Autowired
	private OpServiceQueryService  opServiceQueryService;
	@Autowired
	private PermitManageService  permitManageService;
	
	private  final Logger     logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("list")
	public String list(ModelMap model){
		
		List<Partner> partnerlist = partnerQueryService.getPartnerList("");
		model.addAttribute("partners",JsonData.toJson(partnerlist));
		
		List<OpService> servicelist = opServiceQueryService.getOpServiceList("","");
		model.addAttribute("services",JsonData.toJson(servicelist));

		
		
		return "permit/list";
	}
	
	@RequestMapping("queryService")
	public @ResponseBody Map<String,Object> queryService(Long partnerId){
		List<OpService> servicelist = opServiceQueryService.getOpServiceList("","");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("Rows", servicelist);
		return map;
	}
	
	@RequestMapping("query")
	public @ResponseBody String query(Long partnerId){
		List<PartnerService> permitlist = permitService.getPermitList(partnerId);
		String idList = "";
		for(PartnerService partnerService:permitlist){
			idList += partnerService.getServiceId();
			idList += ",";
		}
		return idList;
	}
	
	@RequestMapping("save")
	public @ResponseBody Map<String,String> save(Long partnerId,String services){
		Map<String,String> retMap = new HashMap<String,String>();
		try{	
			String[] servicesArr = services.split("\\|");
			List<Long> serviceList = new ArrayList<Long>();
			for(String service:servicesArr){
				if(!StringUtils.isEmpty(service)){
					serviceList.add(Long.parseLong(service));
				}
			}
			permitManageService.savePermits(partnerId, serviceList);
			retMap.put("message", "保存成功");
		}catch(Exception e){
			logger.error("更新权限失败",e);
			retMap.put("message", "更新权限失败");
		}
		return retMap;
		
	}
	
//	@RequestMapping("queryService")
//	public @ResponseBody String getAllService(){
//		List<OpService> servicelist = opServiceQueryService.getOpServiceList("","");
//		return JsonData.toJson(servicelist);
//	}
	
}
