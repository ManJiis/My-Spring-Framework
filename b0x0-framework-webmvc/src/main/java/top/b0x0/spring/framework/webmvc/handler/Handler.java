package top.b0x0.spring.framework.webmvc.handler;


import top.b0x0.spring.framework.webmvc.servlet.RequestHandlerChain;

/**
 * 请求执行器 Handler
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface Handler {
    /**
     * 请求的执行器
     *
     * @param handlerChain {@link RequestHandlerChain}
     * @return 是否执行下一个
     * @throws Exception Exception
     */
    boolean handle(final RequestHandlerChain handlerChain) throws Exception;
}
