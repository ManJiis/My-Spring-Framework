package top.b0x0.spring.framework.webmvc.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.annotation.Autowired;
import top.b0x0.spring.framework.webmvc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.Service;
import top.b0x0.spring.framework.webmvc.annotation.*;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.ClassInfo;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.RequestPathInfo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * DispatcherServlet 所有 http 请求都由此 Servlet 转发
 *
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    static String system_os_name = "Windows";

    static {
        system_os_name = System.getProperty("os.name");
        log.debug("system_os_name = " + system_os_name);
    }

    /**
     * 保存所有扫描包的list
     */
    List<String> classList = new CopyOnWriteArrayList<>();
    /**
     * 创建一个容器 map 保存注解后面的值为 key,对象为value
     */
    Map<String, Object> beanMap = new ConcurrentHashMap<>();
    /**
     * 创建一个容器 map 存放路径方法
     */
    Map<RequestPathInfo, ClassInfo> urlMappingMap = new ConcurrentHashMap<>();

    /**
     * load-on-startup 0
     * 由于web.xml中配置了此项,tomcat启动时会先执行这个方法
     */
    @Override
    public void init(ServletConfig servletConfig) {
        // 1. 扫描top.b0x0.spring.mvc.controller
        doScan("top.b0x0");

        // 2. 实例化
        doInstance();

        // 3. 注入
        doAutowired();

        // 4. 匹配路径
        urlMapping();

        log.info("classNameList.size(): {} , classNameList: {}", classList.size(), classList);
        log.info("beanMap.size(): {} , beanMap: {}", beanMap.entrySet().size(), beanMap);
        log.info("urlMappingMap.size(): {} , urlMappingMap: {}", urlMappingMap.entrySet().size(), urlMappingMap);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取到请求路径

        // 项目名/controller/select
        String uri = req.getRequestURI();
        System.out.println("------------发起请求------------");
        System.out.println("1. RequestURI = " + uri);
        // 项目名
        String context = req.getContextPath();
        // /controller/select--->key
        String path = uri.replace(context, "");

        System.out.println("2. urlMappingMap.get(" + path + ")");
        // /controller/select--->method
        RequestPathInfo pathInfo = new RequestPathInfo();
        pathInfo.setHttpPath(path);

        // 根据请求路径以及请求方法获取相应的Controller类及方法信息
        ClassInfo classInfo = urlMappingMap.get(pathInfo);

        Method invokeMethod = classInfo.getInvokeMethod();
        log.info("method name: {}", invokeMethod.getName());

        // 根据方法名获取类名
        Class<?> controllerClass = classInfo.getControllerClass();
        log.info("class name: {}", controllerClass.getName());

        // 处理请求参数
        Object[] args = handMethod(req, resp, invokeMethod);

        // 获取类实例
        Object instance = beanMap.get(controllerClass.getName());
        log.info(" class instance: {}", instance);
        try {
            System.out.println("3. invoke method start");
            invokeMethod.invoke(instance, args);
            System.out.println("4. invoke method end");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // super.doPost(req, resp);
        System.out.println("4. 请求结束");
    }

    /**
     * 保存Controller的请求路径
     */
    public void urlMapping() {

        RequestPathInfo requestPathInfo;
        ClassInfo classInfo;

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            // 类上有Controller注解
            if (clazz.isAnnotationPresent(Controller.class)) {
                RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
                // 获得类上请求路径
                String classRequestPath = classRequestMapping.value();
                String urlMappingMapKey;
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping request = method.getAnnotation(RequestMapping.class);
                        String methodPath = request.value();
                        if ("".equals(classRequestPath) || "/".equals(classRequestPath)) {
                            urlMappingMapKey = methodPath;
                        } else {
                            urlMappingMapKey = classRequestPath + methodPath;
                        }
                        List<String> keyList = urlMappingMap.keySet().stream().map(RequestPathInfo::getHttpPath).collect(Collectors.toList());
                        boolean contains = keyList.contains(urlMappingMapKey);
                        if (contains) {
                            log.error("url mapping duplication ： {}", urlMappingMapKey);
                            System.exit(0);
                        }
                        requestPathInfo = new RequestPathInfo();
                        requestPathInfo.setHttpPath(urlMappingMapKey);

                        classInfo = new ClassInfo();
                        classInfo.setControllerClass(clazz);
                        classInfo.setInvokeMethod(method);

                        // 请求路径为key: /myController/select
                        // 方法为value: public void top.b0x0.spring.mvc.controller.TestController.select(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.lang.String,java.lang.String)
                        urlMappingMap.put(requestPathInfo, classInfo);
                    }
                }
            }
        }
    }

    /**
     * 注入
     */
    public void doAutowired() {
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                Field[] fields = clazz.getFields();
                // Annotation[] annotations = clazz.getAnnotations();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Autowired autowired = field.getAnnotation(Autowired.class);
                        String key = autowired.value();
                        Object value = beanMap.get(key);
                        field.setAccessible(true);
                        try {
                            field.set(instance, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    /**
     * 实例化并添加到容器
     */
    public void doInstance() {
        for (String className : classList) {
            String classPath = className.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(classPath);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    // 实例化对象
                    Object controllerInstance = clazz.newInstance();
                    RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                    String controllerKey = mapping.value();
                    if ("".equals(controllerKey) || "/".equals(controllerKey)) {
                        controllerKey = clazz.getName();
                        Set<String> keySet = beanMap.keySet();
                        boolean contains = keySet.contains(controllerKey);
                        if (contains) {
                            log.error("class bean name duplication ： {}", controllerKey);
                            System.exit(0);
                        }
                    }
//                    log.info("controllerKey: {}", controllerKey);
//                    log.info("controllerInstance: {}", controllerInstance);
                    // key: /myController
                    // value: top.b0x0.spring.mvc.controller.TestController@37e2d5ff
                    beanMap.put(controllerKey, controllerInstance);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    // 实例化对象
                    Object serviceInstance = clazz.newInstance();
                    Service mapping = clazz.getAnnotation(Service.class);
                    String serviceKey = mapping.value();
                    if ("".equals(serviceKey)) {
                        serviceKey = clazz.getName();
                        Set<String> keySet = beanMap.keySet();
                        boolean contains = keySet.contains(serviceKey);
                        if (contains) {
                            log.error("class bean name duplication ： {}", serviceKey);
                            System.exit(0);
                        }
                    }
//                    log.info("serviceKey: {}", serviceKey);
//                    log.info("serviceInstance: {}", serviceInstance);
                    // key: /testService
                    // value: top.b0x0.spring.mvc.service.TestServiceImpl@1ca5c567
                    beanMap.put(serviceKey, serviceInstance);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描包，获取所有类名
     *
     * @param basePackage /
     */
    public void doScan(String basePackage) {
        // 扫描编译好的所有类路径

        // 将 top.b0x0 变成 top/b0x0
        String basePackageName = basePackage.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource("/" + basePackageName);
        if (url == null) {
            log.error("1. The package scan path is abnormal.");
            return;
        }
        // /D:/work-space/ower/My-Spring-Framework/my-spring-mvc/target/my-spring-mvc-1.0-SNAPSHOT/WEB-INF/classes/top/b0x0/
        String urlFile = url.getFile();

        // D:/work-space/ower/My-Spring-Framework/my-spring-mvc/target/my-spring-mvc-1.0-SNAPSHOT/WEB-INF/classes/top/b0x0/
        String fileStr;
        if (system_os_name.startsWith("Win")) {
            // 操作系统是Windows 去掉路径开头斜杠
            fileStr = urlFile.substring(1).replaceAll("\\%[20]+", " ");
        } else {
            // 操作系统不是Windows 不去掉路径开头斜杠
            fileStr = urlFile.replaceAll("\\%[20]+", " ");
        }

        // 得到文件对象
        File file = new File(fileStr);

        // 获取文件夹下的所有文件
        String[] fileList = file.list();
        if (fileList == null) {
            log.error("2. The package scan path is abnormal.");
            return;
        }
        for (String fileName : fileList) {
            File fileObj = new File(fileStr + fileName);
            if (fileObj.isDirectory()) {
                // 递归扫描包名
                doScan(basePackage + "." + fileName);
            } else {
                // 找到class类 top.b0x0.*.class
                String className = basePackage + "." + fileObj.getName();
                classList.add(className);
            }
        }
    }


    public static Object[] handMethod(HttpServletRequest request, HttpServletResponse response, Method method) {
        // 拿到当前执行的方法有哪些参数
        Class<?>[] paramClazzs = method.getParameterTypes();
        // 根据参数的个数,new一个参数的数组,将方法里的所有参数赋值到args来
        Object[] args = new Object[paramClazzs.length];
        int args_i = 0;
        int index = 0;
        for (Class<?> paramClazz : paramClazzs) {
            if (ServletRequest.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = request;
            }
            if (ServletResponse.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = response;
            }
            // 从 0-3 判断有没有requestParam注解,很明显paramClazz为0和1时,不是
            // 当为2和3为requestParam,需要解析
            Annotation[] paramAns = method.getParameterAnnotations()[index];
            if (paramAns.length > 0) {
                for (Annotation paramAn : paramAns) {
                    if (RequestParam.class.isAssignableFrom(paramAn.getClass())) {
                        RequestParam rp = (RequestParam) paramAn;
                        String value = rp.value();
                        // 找到注解里的name和age
                        args[args_i++] = request.getParameter(rp.value());
                    }
                }
            }
            index++;
        }
        return args;
    }

}
