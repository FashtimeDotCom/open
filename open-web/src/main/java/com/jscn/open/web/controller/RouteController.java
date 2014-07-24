package com.jscn.open.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jscn.commons.core.open.OpenResponse;
import com.jscn.commons.core.utils.ClassUtils;
import com.jscn.open.service.route.RouteService;
import com.jscn.open.web.constant.RequestConstant;
import com.jscn.open.web.util.HttpUtils;
import com.jscn.open.web.valid.Validator;


@Controller
@RequestMapping("")
public class RouteController {
	
	@Autowired
	private Validator validator;
	@Autowired
	private RouteService routeService;
	
	private  final Logger     logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("{system}/{serviceName}/{methodName}/{formart}")
	public void  service(RequestPO requestPO,String orderId,HttpServletRequest req,HttpServletResponse resp) throws UnsupportedEncodingException{
		Map<String,String> paramMap = HttpUtils.getServiceParameters(req);
		requestPO.setIp(HttpUtils.getIpAddr(req));
		logger.info("收到请求：[{}],[{}]",ClassUtils.getObjectLogInfo(requestPO),ClassUtils.getObjectLogInfo(paramMap));
		String message = validator.valid(requestPO,paramMap);
		OpenResponse response = new OpenResponse();
		if(message!=null){
			response.setErrorMessage(message);
			response.setIsSuccess("false");
		}else{
			try {
				String result = routeService.excute(requestPO.getSystem()+"_"+requestPO.getServiceName(),requestPO.getMethodName(),requestPO.getFormart(),paramMap);
				sendResponse(resp,result,requestPO.getInputCharset());
				return;
			}catch (Exception e) {
                logger.error("调用服务失败",e);
                response.setErrorMessage("系统异常");
                response.setIsSuccess("false");
            }
		}
		if(RequestConstant.XML.equalsIgnoreCase(requestPO.getFormart())){
		    sendResponse(resp,response.toXml(),requestPO.getInputCharset());
		}else{
		    sendResponse(resp,response.toJson(),requestPO.getInputCharset());
		}
		
	}
	

	/**
	 * 返回结果
	 * @param resp
	 * @param response
	 */
	private void sendResponse(HttpServletResponse resp,String response,String charset){
		try {
			resp.getWriter().print(URLEncoder.encode(response,charset));
			logger.info("响应：[{}]",response);
		} catch (IOException e) {
			logger.error("返回出错",e);
		}
	}

}
