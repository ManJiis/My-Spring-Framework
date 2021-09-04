package top.b0x0.spring.framework.webmvc.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.boot.ApplicationBoot;
import top.b0x0.spring.framework.webmvc.handler.Handler;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 普通url请求执行
 * 主要处理静态资源
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class SimpleUrlHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(UrlMappingHandler.class);


    /**
     * tomcat默认RequestDispatcher的名称
     * TODO: 其他服务器默认的RequestDispatcher.如WebLogic为FileServlet
     */
    private static final String TOMCAT_DEFAULT_SERVLET = "default";

    /**
     * 默认的RequestDispatcher,处理静态资源
     */
    private RequestDispatcher defaultServlet;

    public SimpleUrlHandler(ServletContext servletContext) {
        defaultServlet = servletContext.getNamedDispatcher(TOMCAT_DEFAULT_SERVLET);

        if (null == defaultServlet) {
            throw new RuntimeException("there is no default servlet");
        }

        log.info("The default servlet for serving static resource is [{}]", TOMCAT_DEFAULT_SERVLET);
    }


    @Override
    public boolean handle(final RequestHandlerChain handlerChain) throws Exception {
        if (isStaticResource(handlerChain.getRequestPath())) {
            defaultServlet.forward(handlerChain.getRequest(), handlerChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否为静态资源
     *
     * @param url 请求路径
     * @return 是否为静态资源
     */
    private boolean isStaticResource(String url) {
        return url.startsWith(ApplicationBoot.getConfiguration().getAssetPath());
    }
}
