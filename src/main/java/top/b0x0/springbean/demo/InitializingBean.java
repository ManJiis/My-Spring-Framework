package top.b0x0.springbean.demo;

/**
 * InitializingBean
 *
 * @author TANG
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
