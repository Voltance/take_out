package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


//        1、获取本次请求的URI
//        2、判断本次请求是否需要处理
//        3、如果不需要处理，则直接放行
//        4、判断登录状态，如果已登录，则直接放行
//        5、如果未登录则返回未登录结果

//        判断地址  从地址确定
        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        log.info("拦截到请求：{}",requestURI);
        boolean check = check(urls, requestURI);
        // 符合地址要求
        if (check) {
            log.info("{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        // 查看是否登录了
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户{}登录", request.getSession().getAttribute("employee"));

            BaseContext.setCurrent((Long) request.getSession().getAttribute("employee"));

            filterChain.doFilter(request, response);
            return;
        }

        // 判断登录状态
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户登录,id为{}", request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrent(userId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        // 返回前端需要的数据 NOTLOGIN
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        ;
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) return true;
        }
        return false;
    }
}
