package top.b0x0.spring.framework.webmvc.render.impl;


import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.servlet.RequestHandlerChain;

/**
 * 默认渲染 200
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public class DefaultRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        int status = handlerChain.getResponseStatus();
        handlerChain.getResponse().setStatus(status);
    }
}
