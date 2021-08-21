package top.b0x0.spring.framework.webmvc.servlet.helperbean;


/**
 * PathInfo 存储http相关信息
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public class RequestPathInfo {
    /**
     * http请求方法
     */
    private String httpMethod;

    /**
     * http请求路径
     */
    private String httpPath;

    public RequestPathInfo() {
    }

    public RequestPathInfo(String httpMethod, String httpPath) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestPathInfo requestPathInfo = (RequestPathInfo) o;

        if (httpMethod != null ? !httpMethod.equals(requestPathInfo.httpMethod) : requestPathInfo.httpMethod != null) return false;
        return httpPath != null ? httpPath.equals(requestPathInfo.httpPath) : requestPathInfo.httpPath == null;
    }

    @Override
    public int hashCode() {
        int result = httpMethod != null ? httpMethod.hashCode() : 0;
        result = 31 * result + (httpPath != null ? httpPath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PathInfo{" +
                "httpMethod='" + httpMethod + '\'' +
                ", httpPath='" + httpPath + '\'' +
                '}';
    }
}
