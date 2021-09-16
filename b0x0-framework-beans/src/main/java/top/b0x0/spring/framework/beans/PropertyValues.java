package top.b0x0.spring.framework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 实例成员属性
 *
 * @author ManJiis
 * @since 2021-09-04
 * @since JDK1.8
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addProperty(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }
}
