package top.b0x0.spring.framework.webmvc.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.handler.Handler;
import top.b0x0.spring.framework.webmvc.handler.impl.InternalErrorRender;
import top.b0x0.spring.framework.webmvc.render.impl.DefaultRender;
import top.b0x0.spring.framework.webmvc.render.Render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * http 请求处理链
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public class RequestHandlerChain {
    private static final Logger log = LoggerFactory.getLogger(RequestHandlerChain.class);

    /**
     * Handler迭代器
     * {@link Handler}
     */
    private Iterator<Handler> handlerIt;

    /**
     * 请求request
     * {@link HttpServletRequest}
     */
    private HttpServletRequest request;

    /**
     * 请求response
     * {@link HttpServletResponse}
     */
    private HttpServletResponse response;

    /**
     * 请求http方法
     */
    private String requestMethod;

    /**
     * 请求http路径
     */
    private String requestPath;

    /**
     * 请求状态码
     */
    private int responseStatus;

    /**
     * 请求结果处理器
     */
    private Render render;

    public RequestHandlerChain(Iterator<Handler> handlerIt, HttpServletRequest request, HttpServletResponse response) {
        this.handlerIt = handlerIt;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseStatus = HttpServletResponse.SC_OK;
    }

    /**
     * 执行请求链
     */
    public void doHandlerChain() {
        try {
            while (handlerIt.hasNext()) {
                if (!handlerIt.next().handle(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("doHandlerChain error", e);
            render = new InternalErrorRender();
        }
    }

    /**
     * 执行处理器
     */
    public void doRender() {
        if (null == render) {
            render = new DefaultRender();
        }
        try {
            render.render(this);
        } catch (Exception e) {
            log.error("doRender", e);
            throw new RuntimeException(e);
        }
    }

    public static Logger getLog() {
        return log;
    }

    public Iterator<Handler> getHandlerIt() {
        return handlerIt;
    }

    public void setHandlerIt(Iterator<Handler> handlerIt) {
        this.handlerIt = handlerIt;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Render getRender() {
        return render;
    }

    public void setRender(Render render) {
        this.render = render;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestHandlerChain that = (RequestHandlerChain) o;

        if (responseStatus != that.responseStatus) return false;
        if (handlerIt != null ? !handlerIt.equals(that.handlerIt) : that.handlerIt != null) return false;
        if (request != null ? !request.equals(that.request) : that.request != null) return false;
        if (response != null ? !response.equals(that.response) : that.response != null) return false;
        if (requestMethod != null ? !requestMethod.equals(that.requestMethod) : that.requestMethod != null)
            return false;
        if (requestPath != null ? !requestPath.equals(that.requestPath) : that.requestPath != null) return false;
        return render != null ? render.equals(that.render) : that.render == null;
    }

    @Override
    public int hashCode() {
        int result = handlerIt != null ? handlerIt.hashCode() : 0;
        result = 31 * result + (request != null ? request.hashCode() : 0);
        result = 31 * result + (response != null ? response.hashCode() : 0);
        result = 31 * result + (requestMethod != null ? requestMethod.hashCode() : 0);
        result = 31 * result + (requestPath != null ? requestPath.hashCode() : 0);
        result = 31 * result + responseStatus;
        result = 31 * result + (render != null ? render.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestHandlerChain{" +
                "handlerIt=" + handlerIt +
                ", request=" + request +
                ", response=" + response +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", responseStatus=" + responseStatus +
                ", render=" + render +
                '}';
    }
}
