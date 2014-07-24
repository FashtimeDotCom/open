package com.jscn.open.admin.controller.service;

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
import com.jscn.open.dao.OpService;
import com.jscn.open.service.opservice.OpServiceManageService;
import com.jscn.open.service.opservice.OpServiceQueryService;


@Controller
@RequestMapping("service")
public class SeviceController {
	
	@Autowired
	private OpServiceQueryService serviceQueryService;
	@Autowired
	private OpServiceManageService serviceManageService;
	
	private  final Logger     logger = LoggerFactory.getLogger(getClass());

	
	@RequestMapping("list")
	public String list(ModelMap model){
		
		List<OpService> list = serviceQueryService.getOpServiceList("", "");
		model.addAttribute("service",JsonData.toJson(list));
		return "service/list";
	}
	
	@RequestMapping("save")
	public @ResponseBody Map<String,String> save(String changedServices,String addedServices){
		Map<String,String> retMap = new HashMap<String,String>();
		OpService[] changedService = JsonUtil.json2Object(changedServices, OpService[].class);
		OpService[] addedService = JsonUtil.json2Object(addedServices, OpService[].class);
		try {
			serviceManageService.saveService(changedService,addedService);
			retMap.put("message","更新成功");
			retMap.put("refresh","true");
		} catch (BusinessException e) {
			logger.error("更新服务出错",e);
			retMap.put("message",e.getMessage());
		}catch (Exception e) {
			logger.error("更新服务出错",e);
			retMap.put("message","系统异常");
		}
		return retMap;

	}
	
	@RequestMapping("delete")
	public @ResponseBody Map<String,String> delete(String serviceStr){
		Map<String,String> retMap = new HashMap<String,String>();
		OpService[] services = JsonUtil.json2Object(serviceStr, OpService[].class);
		if(services!=null&&services.length>0){
			try {
				serviceManageService.deleteService(services);
				retMap.put("message","删除成功");
			} catch (BusinessException e) {
				logger.error("删除服务出错",e);
				retMap.put("message",e.getMessage());
			}catch (Exception e) {
				logger.error("删除服务出错",e);
				retMap.put("message","系统异常");
			}
		}else{
			retMap.put("message","请选择需要删除的数据");
		}
		retMap.put("refresh","true");
		return retMap;
	}
	
}
