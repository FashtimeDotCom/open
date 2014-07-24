package com.jscn.open.admin.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {


    /**
     * Take this filter out of service.
     */
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute(User.SESSION_KEY);
        if (user==null && !httpRequest.getPathInfo().equals("/login")
                && !httpRequest.getPathInfo().equals("/logon")) {
            setResponse(httpRequest,httpResponse);
            return;
        }
        chain.doFilter(request, response);
    }

    private void setResponse(HttpServletRequest request ,HttpServletResponse response) throws IOException{
     // 普通页面请求
        String rootPath = request.getContextPath() + "/manage/login";
        PrintWriter out = null;
        out = response.getWriter();
        out.println("<script type='text/javascript'>");  
        out.println("window.open('"+rootPath+"','_top')");  
        out.println("</script>");  
        out.close();
    }
    /**
     * Place this filter into service.
     * 
     * @param filterConfig The filter configuration object
     * @exception ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
