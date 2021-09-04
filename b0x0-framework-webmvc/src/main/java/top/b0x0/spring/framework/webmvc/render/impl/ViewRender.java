package top.b0x0.spring.framework.webmvc.render.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.boot.ApplicationBoot;
import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 渲染页面
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class ViewRender implements Render {
    private static final Logger log = LoggerFactory.getLogger(ViewRender.class);

    private ModelAndView modelAndView;

    public ViewRender(Object modelAndView) {
        if (modelAndView instanceof ModelAndView) {
            this.modelAndView = (ModelAndView) modelAndView;
        } else if (modelAndView instanceof String) {
            this.modelAndView = new ModelAndView().setView((String) modelAndView);
        } else {
            throw new RuntimeException("返回类型不合法");
        }
    }

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        HttpServletRequest req = handlerChain.getRequest();
        HttpServletResponse resp = handlerChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        model.forEach(req::setAttribute);
        req.getRequestDispatcher(ApplicationBoot.getConfiguration().getViewPath() + path).forward(req, resp);
    }
}
