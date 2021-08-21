package top.b0x0.spring.bean;

/**
 * @author TANG
 * @date 2021-07-05
 * @since 1.8
 */
public class TestA {
    public static void main(String[] args) {
        String s = "top.b0x0.springbean.service.MyService.class";
        String filePath = s.substring(s.indexOf("top"), s.indexOf(".class"));
        System.out.println("filePath = " + filePath);

    }
}
