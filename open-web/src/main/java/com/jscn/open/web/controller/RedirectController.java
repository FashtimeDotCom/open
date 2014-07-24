package com.jscn.open.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.utils.ClassUtils;
import com.jscn.open.service.opservice.OpServiceQueryService;
import com.jscn.open.web.util.HttpUtils;
import com.jscn.open.web.valid.Validator;

@Controller
@RequestMapping("")
public class RedirectController {
    
    @Autowired
    private Validator validator;
    
    @Autowired
    private OpServiceQueryService opServiceQueryService;
    private  final Logger     logger = LoggerFactory.getLogger(getClass());

    /**
     * 调用服务后重定向
     * @param resp
     */
    @RequestMapping("/redirect/{system}/{serviceName}")
    public String  serviceCallBack(RequestPO requestPO,HttpServletRequest request,HttpServletResponse resp,ModelMap model){
        Map<String,String> paramMap = HttpUtils.getServiceParameters(request);
        requestPO.setIp(HttpUtils.getIpAddr(request));
        logger.info("收到请求：[{}],[{}]",ClassUtils.getObjectLogInfo(requestPO),paramMap);
        String message = validator.valid(requestPO,HttpUtils.getServiceParameters(request));
        if(message!=null){
            model.addAttribute("message",message);
            logger.error(message);
            return "error/error";
        }
        
        String url = "";
        try {
            url = opServiceQueryService.getUrlByServiceName(requestPO.getSystem()+"_"+requestPO.getServiceName());
        } catch (BusinessException e) {
            logger.error("查找url错误",e);
            model.addAttribute("message",e.getMessage());
            return "error/error";
        }
        url = url+HttpUtils.getRequestInfo(request);
        logger.info("转发url=[{}]",url);
        try {
            resp.sendRedirect(url);
        } catch (IOException e) {
            logger.error("重定向错误",e);
            model.addAttribute("message",e.getMessage());
            return "error/error";
        }
        logger.info("请求 响应结束");
        return null;
    }
}
