package top.b0x0.spring.framework.webmvc.server;

/**
 * 服务器 interface
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public interface Server {
    /**
     * 启动服务器
     *
     * @throws Exception Exception
     */
    void startServer() throws Exception;

    /**
     * 停止服务器
     *
     * @throws Exception Exception
     */
    void stopServer() throws Exception;
}
