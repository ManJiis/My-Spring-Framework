package top.b0x0.spring.mvc.framework.servlet;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import top.b0x0.spring.mvc.framework.annotation.*;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
    List<String> classNameList = new ArrayList<>();
    /**
     * 创建一个容器 map 保存注解后面的值为 key,对象为value
     */
    Map<String, Object> beanMap = new HashMap<>();
    /**
     * 创建一个容器 map 存放路径方法
     */
    Map<String, Object> urlMappingMap = new HashMap<>();

    /**
     * <load-on-startup>0</load-on-startup>
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

        System.err.println("classNameList.size(): " + classNameList.size() + "classNameList: " + classNameList);
        System.err.println("beanMap.size(): " + beanMap.entrySet().size() + "beanMap: " + beanMap);
        System.err.println("urlMappingMap.size(): " + urlMappingMap.entrySet().size() + "urlMappingMap: " + urlMappingMap);
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
        Method method = (Method) urlMappingMap.get(path);
        Object[] args = handMethod(req, resp, method);
        Object instance = beanMap.get("/" + path.split("/")[1]);

        try {
            System.out.println("3. invoke method start");
            method.invoke(instance, args);
            System.out.println("4. invoke method end");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // super.doPost(req, resp);
        System.out.println("4. 请求结束");
    }

    /**
     * 保存请求路径
     */
    public void urlMapping() {
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            // 类上有MyController注解
            if (clazz.isAnnotationPresent(MyController.class)) {
                MyRequestMapping classRequestMapping = clazz.getAnnotation(MyRequestMapping.class);
                // 获得类上请求路径
                String classRequestPath = classRequestMapping.value();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MyRequestMapping.class)) {
                        MyRequestMapping request = method.getAnnotation(MyRequestMapping.class);
                        String methodPath = request.value();
                        // key: /myController/select
                        // value: public void top.b0x0.spring.mvc.controller.TestController.select(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.lang.String,java.lang.String)
                        urlMappingMap.put(classRequestPath + methodPath, method);
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
            if (clazz.isAnnotationPresent(MyController.class)) {
                Field[] fields = clazz.getFields();
                // Annotation[] annotations = clazz.getAnnotations();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(MyAutowired.class)) {
                        MyAutowired autowired = field.getAnnotation(MyAutowired.class);
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
        for (String className : classNameList) {
            String classPath = className.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(classPath);
                if (clazz.isAnnotationPresent(MyController.class)) {
                    // 实例化对象
                    Object controllerInstance = clazz.newInstance();
                    MyRequestMapping mapping = clazz.getAnnotation(MyRequestMapping.class);
                    String controllerKey = mapping.value();
                    // key: /myController
                    // value: top.b0x0.spring.mvc.controller.TestController@37e2d5ff
                    beanMap.put(controllerKey, controllerInstance);
                } else if (clazz.isAnnotationPresent(MyService.class)) {
                    // 实例化对象
                    Object serviceInstance = clazz.newInstance();
                    MyService mapping = clazz.getAnnotation(MyService.class);
                    String serviceKey = mapping.value();
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
            // top/b0x0/mirrorming_springMVCdemo
            File fileObj = new File(fileStr + fileName);
            if (fileObj.isDirectory()) {
                // 递归扫描包名
                doScan(basePackage + "." + fileName);
            } else {
                // 找到class类 top.b0x0.*.class
                String className = basePackage + "." + fileObj.getName();
                System.out.println("className = " + className);
                classNameList.add(className);
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
                    if (MyRequestParam.class.isAssignableFrom(paramAn.getClass())) {
                        MyRequestParam rp = (MyRequestParam) paramAn;
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

    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("11", "222");
        hashMap.put("22", "222");
        System.out.println("hashMap = " + hashMap);
    }

}
