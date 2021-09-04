package top.b0x0.spring.framework.beans;

/**
 * BeansException
 *
 * @author ManJiis
 * @since 2021-09-04
 * @since JDK1.8
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
