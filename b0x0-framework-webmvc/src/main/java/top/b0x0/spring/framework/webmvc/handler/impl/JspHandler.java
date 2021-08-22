package top.b0x0.spring.framework.webmvc.handler.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.boot.AppBoot;
import top.b0x0.spring.framework.webmvc.handler.Handler;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * jsp请求处理
 * 主要负责jsp资源请求
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class JspHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(JspHandler.class);

    /**
     * jsp请求的RequestDispatcher的名称
     */
    private static final String JSP_SERVLET = "jsp";

    /**
     * jsp的RequestDispatcher,处理jsp资源
     */
    private RequestDispatcher jspServlet;

    public JspHandler(ServletContext servletContext) {
        jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (null == jspServlet) {
            throw new RuntimeException("no jsp servlet");
        }
    }

    @Override
    public boolean handle(final RequestHandlerChain handlerChain) throws Exception {
        if (isPageView(handlerChain.getRequestPath())) {
            jspServlet.forward(handlerChain.getRequest(), handlerChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否为jsp资源
     *
     * @param url 请求路径
     * @return 是否为jsp资源
     */
    private boolean isPageView(String url) {
        return url.startsWith(AppBoot.getConfiguration().getViewPath());
    }
}
