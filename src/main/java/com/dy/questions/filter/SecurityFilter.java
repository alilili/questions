package com.dy.questions.filter;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/7 14:49
 * @description : 安全认证
 */
@Service
@WebFilter(filterName = "securityFilter",urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();

        if(url.contains("form")){
            long l = System.currentTimeMillis();
            String key = DigestUtils.md2Hex("date_"+l+"sec");
            request.getSession().setAttribute("time",l);
            request.getSession().setAttribute("key", key);
            filterChain.doFilter(servletRequest,servletResponse);
        }

        if(url.contains("publish/save")){
            HttpSession session = request.getSession();

            long time = (Long)session.getAttribute("time");
            String key = (String)session.getAttribute("key");

            String reqKey = DigestUtils.md2Hex("date_"+time+"sec");

            if(!reqKey.equals(key)){
                System.out.println("【securityFilter】安全校验不匹配。key："+key);
                response.sendRedirect("/resources/error.html");
                return;
            }
//            long l = System.currentTimeMillis();
//            key = DigestUtils.md2Hex("date_"+l+"sec");
//            session.setAttribute("time",l);
//            session.setAttribute("key",key);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}