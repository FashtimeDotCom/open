package com.jscn.open.service.route;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.commons.core.http.HttpClientUtil;
import com.jscn.open.dao.OpService;
import com.jscn.open.service.opservice.OpServiceQueryService;

@Service
public class HttpRouteServiceImpl implements RouteService{
    
    @Autowired
    private OpServiceQueryService opServiceQueryService;
    //与服务交互编码，统一为utf-8
    private final static String CHARSET = "utf-8";
    @Override
    public String excute(String beanName, String methodName,String formart, Map<String, String> params){
        
        OpService opservice;
        try {
            opservice = opServiceQueryService.getOpService(beanName, methodName);
        } catch (BusinessException e) {
            throw new SystemException("查找服务异常",e);
        }
        params.put("BEAN_NAME", beanName);
        params.put("METHOD_NAME", methodName);
        params.put("FORMART", formart);
        String response = HttpClientUtil.post(opservice.getUrl(), params);
        try {
            return URLDecoder.decode(response,CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("系统异常",e);
        }
    }

}
