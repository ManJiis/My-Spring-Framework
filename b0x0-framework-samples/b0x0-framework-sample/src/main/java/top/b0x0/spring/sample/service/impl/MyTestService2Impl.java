package top.b0x0.spring.sample.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.ioc.annotation.Service;
import top.b0x0.spring.sample.service.IMyTestService;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@Service
public class MyTestService2Impl implements IMyTestService {
    private static final Logger log = LoggerFactory.getLogger(MyTestService2Impl.class);

    @Override
    public String select(String name, String age) {
        return "service-2 name: " + name + " , age: " + age;
    }

}
