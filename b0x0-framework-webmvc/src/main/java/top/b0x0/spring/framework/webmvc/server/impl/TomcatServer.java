package top.b0x0.spring.framework.webmvc.server.impl;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.webmvc.boot.AppBoot;
import top.b0x0.spring.framework.webmvc.boot.AppConfiguration;
import top.b0x0.spring.framework.webmvc.server.Server;
import top.b0x0.spring.framework.webmvc.servlet.DispatcherServlet;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Tomcat 服务器
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class TomcatServer implements Server {
    private static final Logger log = LoggerFactory.getLogger(TomcatServer.class);

    private Tomcat tomcat;

    public TomcatServer() {
        new TomcatServer(AppBoot.getConfiguration());
    }

    public TomcatServer(AppConfiguration appConfiguration) {
        try {
            this.tomcat = new Tomcat();
            tomcat.setBaseDir(appConfiguration.getDocBase());
            tomcat.setPort(appConfiguration.getServerPort());

            File root = getRootFolder(appConfiguration);
            File webContentFolder = new File(root.getAbsolutePath(), appConfiguration.getResourcePath());
            if (!webContentFolder.exists()) {
                webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
            }

            log.info("Tomcat:configuring app with basedir: [{}]", webContentFolder.getAbsolutePath());
            StandardContext ctx = (StandardContext) tomcat.addWebapp(appConfiguration.getContextPath(), webContentFolder.getAbsolutePath());
            ctx.setParentClassLoader(this.getClass().getClassLoader());

            WebResourceRoot resources = new StandardRoot(ctx);
            ctx.setResources(resources);

            // 配置自定义的 DispatcherServlet
            tomcat.addServlet(appConfiguration.getContextPath(), "dispatcherServlet", new DispatcherServlet()).setLoadOnStartup(0);
            ctx.addServletMappingDecoded("/*", "dispatcherServlet");
        } catch (Exception e) {
            log.error("failed to initialize tomcat", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startServer() throws Exception {
        tomcat.start();
        String address = tomcat.getServer().getAddress();
        int port = tomcat.getConnector().getPort();
        log.info("local address: http://{}:{}", address, port);
        tomcat.getServer().await();
    }

    @Override
    public void stopServer() throws Exception {
        tomcat.stop();
    }

    private File getRootFolder(AppConfiguration appConfiguration) {
        try {
            File root;
            String runningJarPath = appConfiguration.getBootClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            log.info("Tomcat:application resolved root folder: [{}]", root.getAbsolutePath());
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
