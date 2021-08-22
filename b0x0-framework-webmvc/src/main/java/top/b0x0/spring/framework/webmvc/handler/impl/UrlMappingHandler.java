package top.b0x0.spring.framework.webmvc.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.common.util.CastUtil;
import top.b0x0.spring.framework.common.util.ValidateUtil;
import top.b0x0.spring.framework.ioc.core.BeanContext;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;
import top.b0x0.spring.framework.webmvc.annotation.RequestParam;
import top.b0x0.spring.framework.webmvc.annotation.ResponseBody;
import top.b0x0.spring.framework.webmvc.handler.Handler;
import top.b0x0.spring.framework.webmvc.render.Render;
import top.b0x0.spring.framework.webmvc.render.impl.JsonRender;
import top.b0x0.spring.framework.webmvc.render.impl.NotFoundRender;
import top.b0x0.spring.framework.webmvc.render.impl.ViewRender;
import top.b0x0.spring.framework.webmvc.handler.RequestHandlerChain;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.ClassInfo;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.RequestPathInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * url mapping handler
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class UrlMappingHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(UrlMappingHandler.class);

    /**
     * 创建一个容器 map 存放路径方法
     */
    Map<RequestPathInfo, ClassInfo> urlMappingMap = new ConcurrentHashMap<>();

    BeanContext beanContext;

    public UrlMappingHandler() {
        beanContext = BeanContext.getInstance();
        this.initUrlMappingMap();
    }

    /**
     * 设置请求结果执行器
     *
     * @param result       controller执行后的结果
     * @param classInfo    controller信息
     * @param handlerChain RequestHandlerChain
     */
    private void setRender(Object result, ClassInfo classInfo, RequestHandlerChain handlerChain) {
        if (null == result) {
            return;
        }
        Render render;
        boolean isJsonClass = classInfo.getControllerClass().isAnnotationPresent(ResponseBody.class);
        boolean isJsonMethod = classInfo.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJsonClass || isJsonMethod) {
            // JSON数据响应
            render = new JsonRender(result);
        } else {
            // 视图渲染
            render = new ViewRender(result);
        }
        handlerChain.setRender(render);
    }

    @Override
    public boolean handle(RequestHandlerChain handlerChain) throws Exception {
        RequestPathInfo requestPathInfo = new RequestPathInfo(handlerChain.getRequestMethod(), handlerChain.getRequestPath());
        ClassInfo classInfo = urlMappingMap.get(requestPathInfo);
        if (null == classInfo) {
            // 请求404
            handlerChain.setRender(new NotFoundRender());
            return false;
        }
        // 执行方法
        Object result = this.invokeRequestMethod(classInfo, handlerChain.getRequest());
        // 设置渲染器
        this.setRender(result, classInfo, handlerChain);
        return true;
    }

    public void initUrlMappingMap() {
        Set<Class<?>> mappingSet = beanContext.getClassesByAnnotation(RequestMapping.class);
        for (Class<?> aClass : mappingSet) {
            this.addMappingPath(aClass);
        }
    }

    /**
     * 保存Controller的请求路径
     */
    public void addMappingPath(Class<?> clz) {
        RequestMapping classRequestMapping = clz.getAnnotation(RequestMapping.class);
        // 获得类上请求路径
        String classReqPath = classRequestMapping.value();
        if (!classReqPath.startsWith("/")) {
            classReqPath = "/" + classReqPath;
        }
        String reqUrl;
        RequestPathInfo requestPathInfo;
        ClassInfo classInfo;
        for (Method method : clz.getMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping request = method.getAnnotation(RequestMapping.class);
                String methodReqPath = request.value();
                if (!methodReqPath.startsWith("/") && !"/".equals(classReqPath)) {
                    methodReqPath = "/" + methodReqPath;
                }
                reqUrl = classReqPath + methodReqPath;
                // 请求路径重复问题
                List<String> keyList = urlMappingMap.keySet().stream().map(RequestPathInfo::getHttpPath).collect(Collectors.toList());
                boolean contains = keyList.contains(reqUrl);
                if (contains) {
                    log.error("url mapping duplication ： {}", reqUrl);
                    System.exit(0);
                }
                // 请求路径信息
                requestPathInfo = new RequestPathInfo(String.valueOf(request.method()), reqUrl);
                // 请求路径对应的类和方法信息
                Map<String, Class<?>> methodParams = this.getMethodParams(method);
                classInfo = new ClassInfo(clz, method, methodParams);

                // 请求路径为key: /myController/select
                // 方法为value: public void top.b0x0.spring.mvc.controller.TestController.select(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.lang.String,java.lang.String)
                urlMappingMap.put(requestPathInfo, classInfo);
            }
        }
    }

    /**
     * 获取执行方法的参数
     *
     * @param method 执行的方法
     * @return 参数别名对应的参数类型
     */
    private Map<String, Class<?>> getMethodParams(Method method) {
        Map<String, Class<?>> map = new HashMap<>();
        for (Parameter parameter : method.getParameters()) {
            RequestParam param = parameter.getAnnotation(RequestParam.class);
            // TODO: 不使用注解匹配参数名字
            if (null == param) {
                throw new RuntimeException("必须有RequestParam指定的参数名");
            }
            map.put(param.value(), parameter.getType());
        }
        return map;
    }

    /**
     * 获取HttpServletRequest中的参数
     *
     * @param request HttpServletRequest
     * @return 参数别名对应的参数值
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        // GET和POST方法是这样获取请求参数的
        request.getParameterMap().forEach((paramName, paramsValues) -> {
            if (ValidateUtil.isNotEmpty(paramsValues)) {
                paramMap.put(paramName, paramsValues[0]);
            }
        });
        // TODO: Body、Path、Header等方式的请求参数获取
        return paramMap;
    }

    /**
     * 执行controller方法
     *
     * @param classInfo controller信息
     * @param request   HttpServletRequest
     * @return 执行方法结果
     */
    private Object invokeRequestMethod(ClassInfo classInfo, HttpServletRequest request) {
        // 统一将参数封装为String
        Map<String, String> requestParams = this.getRequestParams(request);
        // 将参数转为为更加具体的数据类型
        List<Object> methodParams = this.instantiateMethodArgs(classInfo.getMethodParameter(), requestParams);

        // 从单例池找那个取出对应的bean
        Object controller = beanContext.getBean(classInfo.getControllerClass());
        // 获取执行方法
        Method invokeMethod = classInfo.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            // 执行方法
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 实例化方法参数
     *
     * @param methodParams  方法的参数
     * @param requestParams request中的参数
     * @return 方法参数实例集合
     */
    private List<Object> instantiateMethodArgs(Map<String, Class<?>> methodParams, Map<String, String> requestParams) {
        return methodParams.keySet().stream().map(paramName -> {
            Class<?> type = methodParams.get(paramName);
            String requestValue = requestParams.get(paramName);
            Object value;
            if (null == requestValue) {
                value = CastUtil.primitiveNull(type);
            } else {
                value = CastUtil.convert(type, requestValue);
                // TODO: 实现非原生类的参数实例化
            }
            return value;
        }).collect(Collectors.toList());
    }
}
