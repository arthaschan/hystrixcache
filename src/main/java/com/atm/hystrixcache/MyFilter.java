package com.atm.hystrixcache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/*
 * 需要配置一下：让Spring容器知道这个Filter我们需要用到
 *
 * urlPatterns="/*"，拦截全部请求
 *
 * filterName="hystrixFilter"，过滤器名称
 */
@WebFilter(urlPatterns = "/*", filterName = "hystrixFilter")
public class MyFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        // 对每一个请求进行拦截，拦截之后干什么呢？

        System.out.println("开始执行过滤器....");
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();

        try {

            chain.doFilter(request, response);

        } catch (Exception e) {

        } finally {
            ctx.shutdown();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}