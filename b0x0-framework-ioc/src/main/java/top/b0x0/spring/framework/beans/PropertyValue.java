package top.b0x0.spring.framework.beans;

/**
 * 实例成员属性
 *
 * @author ManJiis
 * @since 2021-09-04
 * @since JDK1.8
 */
public class PropertyValue {
    private String name;
    private Object value;

    public PropertyValue() {
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
