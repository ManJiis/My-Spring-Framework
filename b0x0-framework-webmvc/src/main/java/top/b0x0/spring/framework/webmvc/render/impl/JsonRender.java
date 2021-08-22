package top.b0x0.spring.framework.webmvc.render.impl;

import com.alibaba.fastjson.JSON;
import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;

import java.io.PrintWriter;

/**
 * 渲染json
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class JsonRender implements Render {

    private Object jsonData;

    public JsonRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        // 设置响应头
        handlerChain.getResponse().setContentType("application/json");
        handlerChain.getResponse().setCharacterEncoding("UTF-8");
        // 向响应中写入数据
        try (PrintWriter writer = handlerChain.getResponse().getWriter()) {
            writer.write(JSON.toJSONString(jsonData));
            writer.flush();
        }
    }
}
