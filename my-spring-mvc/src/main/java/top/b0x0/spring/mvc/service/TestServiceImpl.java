package top.b0x0.spring.mvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.mvc.framework.annotation.MyService;
import top.b0x0.spring.mvc.framework.servlet.DispatcherServlet;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@MyService("/testServiceImpl")
public class TestServiceImpl implements ITestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public String select(String name, String age) {
        return "name: " + name + " , age: " + age;
    }

}
