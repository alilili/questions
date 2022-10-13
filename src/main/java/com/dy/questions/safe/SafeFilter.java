package com.dy.questions.safe;

import com.alibaba.druid.util.StringUtils;
import com.dy.questions.utils.IpLocalUtil;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 安全过滤器
 *
 * security.properties过滤配置需要校验的地址，访问需要对应配置ip才能访问
 */
@Component
public class SafeFilter implements Filter {

    private static List<String> CheckPathList;
    private static List<String> AdminIpList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CheckPathList = new ArrayList<>();
        AdminIpList = new ArrayList<>();
        AdminIpList.add("http://127.0.0.1");
        AdminIpList.add("http://localhost");

        loadSecurityProperties();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        String url = ((RequestFacade) servletRequest).getRequestURL().toString();

        if(!StringUtils.isEmpty(url) && (url.endsWith(".css") || url.endsWith(".js"))){
            chain.doFilter(servletRequest, servletResponse);
            return ;
        }

        for(String path : CheckPathList){
            if(url.contains(path) && !url.contains("http://127.0.0.1") && !url.contains("http://localhost")){

                String ip = IpLocalUtil.getIPAddress((HttpServletRequest)servletRequest);
                boolean flag = false;
                for(String s : AdminIpList){
                    if(s.contains(ip)){
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    throw new ServletException("您无权限访问该页面,ip:"+ip);
                }
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    //获取配置文件数据
    private void loadSecurityProperties(){
        InputStream is = SafeFilter.class.getClassLoader().getResourceAsStream("config/security.properties");
        Properties pro = new Properties();
        try {
            pro.load(is);

            for (String key : pro.stringPropertyNames()) {

                if(key.startsWith("admin-ip")){
                    String urls = pro.getProperty(key);
                    String[] urlArray = urls.split(";");

                    for(String s : urlArray){
                        if(!StringUtils.isEmpty(s))
                            AdminIpList.add(s.replace("*",""));
                    }
                }

                if(key.startsWith("check-path")){
                    String urls = pro.getProperty(key);
                    String[] urlArray = urls.split(";");

                    for(String s : urlArray){

                        if(!StringUtils.isEmpty(s))
                            CheckPathList.add(s.replace("*",""));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
