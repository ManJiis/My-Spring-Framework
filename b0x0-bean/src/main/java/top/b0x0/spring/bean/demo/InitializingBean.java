package top.b0x0.spring.bean.demo;

/**
 * InitializingBean
 *
 * @author TANG
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
