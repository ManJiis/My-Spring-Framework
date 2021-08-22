package top.b0x0.spring.framework.webmvc.boot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.ioc.Ioc;
import top.b0x0.spring.framework.ioc.core.BeanContext;
import top.b0x0.spring.framework.webmvc.server.Server;
import top.b0x0.spring.framework.webmvc.server.impl.TomcatServer;

/**
 * Java App Starter
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppBoot {
    private static final Logger log = LoggerFactory.getLogger(AppBoot.class);

    /**
     * 全局配置
     */
    private static AppConfiguration appConfiguration = AppConfiguration.builder().build();

    /**
     * 默认服务器
     */
    private static Server server;

    /**
     * 启动
     *
     * @param bootClass 启动服务器的类
     */
    public static void run(Class<?> bootClass) {
        run(AppConfiguration.builder().bootClass(bootClass).build());
    }

    /**
     * 启动
     *
     * @param bootClass 启动服务器的类
     * @param port      服务器端口
     */
    public static void run(Class<?> bootClass, int port) {
        new AppBoot().start(AppConfiguration.builder().bootClass(bootClass).serverPort(port).build());
    }

    /**
     * 启动
     *
     * @param appConfiguration 配置
     */
    public static void run(AppConfiguration appConfiguration) {
        new AppBoot().start(appConfiguration);
    }

    /**
     * 获取server
     *
     * @return 项目服务器
     */
    public static Server getServer() {
        return server;
    }

    /**
     * 获取全局配置
     *
     * @return 全局配置
     */
    public static AppConfiguration getConfiguration() {
        return appConfiguration;
    }

    /**
     * 初始化
     *
     * @param appConfiguration 配置
     */
    private void start(AppConfiguration appConfiguration) {
        try {
            AppBoot.appConfiguration = appConfiguration;

            // tomcat启动时加载所有class
            String basePackage = appConfiguration.getBootClass().getPackage().getName();
            BeanContext.getInstance().loadBeans(basePackage);

            // aop ioc
//            new Aop().doAop();
            new Ioc().doInstance();

            server = new TomcatServer(appConfiguration);
            // 启动tomcat
            server.startServer();
        } catch (Exception e) {
            log.error("Java App Start error：{}", e.getMessage(), e);
        }
    }

}
