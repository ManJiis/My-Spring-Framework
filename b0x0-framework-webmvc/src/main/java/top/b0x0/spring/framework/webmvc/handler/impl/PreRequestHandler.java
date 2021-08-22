package top.b0x0.spring.framework.webmvc.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.handler.Handler;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

/**
 * 请求预处理
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class PreRequestHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(PreRequestHandler.class);

    @Override
    public boolean handle(final RequestHandlerChain handlerChain) throws Exception {
        // 设置请求编码方式
        handlerChain.getRequest().setCharacterEncoding("UTF-8");
        String requestPath = handlerChain.getRequestPath();
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            handlerChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("[b0x0 framework] {} {}", handlerChain.getRequestMethod(), handlerChain.getRequestPath());
        return true;
    }
}
