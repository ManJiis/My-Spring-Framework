package top.b0x0.spring.framework.webmvc.boot;

import lombok.Builder;
import lombok.Getter;

/**
 * 服务器相关配置
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@Builder
@Getter
public class AppConfiguration {

    /**
     * 启动类
     */
    private final Class<?> bootClass;

    /**
     * 资源目录
     */
    @Builder.Default
    private final String resourcePath = "src/main/resources/";

    /**
     * jsp目录
     */
    @Builder.Default
    private final String viewPath = "/templates/";

    /**
     * 静态文件目录
     */
    @Builder.Default
    private final String assetPath = "/static/";

    /**
     * 端口号
     */
    @Builder.Default
    private final int serverPort = 9090;

    /**
     * tomcat docBase目录
     */
    @Builder.Default
    private final String docBase = "";

    /**
     * tomcat contextPath目录
     */
    @Builder.Default
    private final String contextPath = "";
}
