package top.b0x0.spring.framework.webmvc.render.impl;

import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染 404
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class NotFoundRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
