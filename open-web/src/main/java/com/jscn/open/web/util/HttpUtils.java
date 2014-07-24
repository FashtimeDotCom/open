package com.jscn.open.web.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.jscn.open.web.constant.RequestConstant;

public class HttpUtils {

	/**
	 * 取得所有参数列表
	 * @param request
	 * @return
	 */
	public static Map<String ,Object> getAllParameters(HttpServletRequest request){
		return WebUtils.getParametersStartingWith(request, "");
	}
	
	/**
	 * 取得所有除了系统参数外的业务参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> getServiceParameters(HttpServletRequest request){
		 Map<String ,Object> map = WebUtils.getParametersStartingWith(request, "");
		 Map<String ,String> retMap = new TreeMap<String,String>();
		 
		 Iterator<String> iterator = map.keySet().iterator();
		 while(iterator.hasNext()){
			 String key = iterator.next();
			 String value = map.get(key).toString();
			 if(!key.equals(RequestConstant.SIGN)&&!key.equals(RequestConstant.INPUT_CHARSET)){
				 retMap.put(key, value);
			 }
		 }
		 
		 return retMap;
	}
	
	public static String getRequestInfo(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (null != parameterMap && !parameterMap.isEmpty()) {
			StringBuilder sb = new StringBuilder("?");
			for (Iterator<Entry<String, String[]>> ite = parameterMap
					.entrySet().iterator(); ite.hasNext();) {
				Entry<String, String[]> paramEntry = ite.next();
				String paramName = paramEntry.getKey();
				String[] paramValueArr = paramEntry.getValue();
				String paramValue = (paramValueArr.length > 0 ? paramValueArr[0]
						: "");
				sb.append(paramName + "=" + paramValue
						+ (ite.hasNext() ? "&" : ""));
			}
			return sb.toString();
		}
		return "";
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
