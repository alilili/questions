package com.dy.questions.utils;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取客户端ip地址
 * @Author: taolb
 * @Date: 2019/6/24 21:41
 */
public class IpLocalUtil {

    private static final Logger log = LoggerFactory.getLogger(IpLocalUtil.class);

    /**
     * 本地ip地址
     */
    public static final String LOCAL_IP_ADDRESS = "127.0.0.1";

    /**
     * 不识别地址
     */
    public static final String UNKNOWN_IP_ADDRESS = "unknown";


    public static String getIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        ip = getIPAddress(request, ip);
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (isMultIps(ip)) {
            ip = ip.substring(0, ip.indexOf(','));
            //log.debug("client ip:{}", ip);
        }
        return ip;
    }

    private static boolean isMultIps(String ip) {
        return ip != null && ip.length() > 15 && ip.indexOf(",") > 0 ;
    }

    private static String getIPAddress(HttpServletRequest request, String ip) {
        log.debug("x-forwarded-for=" + ip);
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("Proxy-Client-IP=" + ip);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP=" + ip);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
            log.debug("x-real-ip=" + ip);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("request.getRemoteAddr()=" + ip);
            if (LOCAL_IP_ADDRESS.equals(ip) || ip.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                    log.debug("local ip:{}", ip);
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return ip;
    }
}
