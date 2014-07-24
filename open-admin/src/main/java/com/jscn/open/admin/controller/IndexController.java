package com.jscn.open.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jscn.commons.core.utils.EncryptUtil;
import com.jscn.open.admin.filter.LoginFilter;
import com.jscn.open.admin.filter.User;
import com.jscn.open.admin.utils.HttpUtils;

@Controller
@RequestMapping("")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("index")
    public String index(ModelMap model) {

        return "index";
    }

    @RequestMapping("login")
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping("logon")
    public String logon(String username, String password, HttpServletRequest request, ModelMap model) {
        model.addAttribute("userName",username);
        if (User.login(username, password)) {
            User user = new User();
            user.setIp(HttpUtils.getIpAddr(request));
            request.getSession().setAttribute(User.SESSION_KEY,user);
            logger.info("用户：[{}] 登入，ip：[{}]" , user.getUserName(), user.getIp());
            return "index";
        } else {
            model.put("message", "用户名或密码不正确");
            return "login";
        }
    }
    
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(User.SESSION_KEY);
        return "login";
    }
    
    
}
