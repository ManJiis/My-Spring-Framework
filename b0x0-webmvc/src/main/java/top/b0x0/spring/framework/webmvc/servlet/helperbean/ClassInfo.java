package top.b0x0.spring.framework.webmvc.servlet.helperbean;


import java.lang.reflect.Method;
import java.util.Map;

/**
 * ClassInfo 存储Controller相关信息
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public class ClassInfo {
    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * 执行的方法
     */
    private Method invokeMethod;

    /**
     * 方法参数别名对应参数类型
     */
    private Map<String, Class<?>> methodParameter;

    public ClassInfo() {
    }

    public ClassInfo(Class<?> controllerClass, Method invokeMethod, Map<String, Class<?>> methodParameter) {
        this.controllerClass = controllerClass;
        this.invokeMethod = invokeMethod;
        this.methodParameter = methodParameter;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public Map<String, Class<?>> getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(Map<String, Class<?>> methodParameter) {
        this.methodParameter = methodParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassInfo that = (ClassInfo) o;

        if (controllerClass != null ? !controllerClass.equals(that.controllerClass) : that.controllerClass != null)
            return false;
        if (invokeMethod != null ? !invokeMethod.equals(that.invokeMethod) : that.invokeMethod != null) return false;
        return methodParameter != null ? methodParameter.equals(that.methodParameter) : that.methodParameter == null;
    }

    @Override
    public int hashCode() {
        int result = controllerClass != null ? controllerClass.hashCode() : 0;
        result = 31 * result + (invokeMethod != null ? invokeMethod.hashCode() : 0);
        result = 31 * result + (methodParameter != null ? methodParameter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ControllerInfo{" +
                "controllerClass=" + controllerClass +
                ", invokeMethod=" + invokeMethod +
                ", methodParameter=" + methodParameter +
                '}';
    }
}
