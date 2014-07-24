package com.jscn.open.admin.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jscn.commons.core.utils.JsonUtil;


/**
 * 满足前端数据格式需要
 * @author Administrator
 *
 */
public class JsonData {


	@SuppressWarnings("rawtypes")
	public static String toJson(List list){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("Rows", list);
		return JsonUtil.objectToJson(map);
	}
	
	
	
}
