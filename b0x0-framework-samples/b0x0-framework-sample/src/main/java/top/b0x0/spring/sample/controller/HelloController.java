package top.b0x0.spring.sample.controller;


import top.b0x0.spring.framework.ioc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;
import top.b0x0.spring.framework.webmvc.annotation.ResponseBody;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@Controller
@RequestMapping
@ResponseBody
public class HelloController {

    @RequestMapping(value = "hello")
    public String hello() {
        return "hello";
    }

}
