package top.b0x0.spring.framework.webmvc.handler.impl;

import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.servlet.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染500
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public class InternalErrorRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
