package top.b0x0.spring.sample;

import top.b0x0.spring.framework.webmvc.boot.AppBoot;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class Application {
    public static void main(String[] args) {
        AppBoot.run(Application.class, 8088);
    }
}
