package top.b0x0.spring.framework.webmvc.render;


import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

/**
 * 渲染请求结果
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface Render {
    /**
     * 执行渲染
     *
     * @param handlerChain {@link RequestHandlerChain}
     * @throws Exception Exception
     */
    void render(RequestHandlerChain handlerChain) throws Exception;
}
