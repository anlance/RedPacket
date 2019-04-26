package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Spring Ioc 环境配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //配置 Spring Ioc 资源
        return new Class<?>[]{RootConfig.class};
    }

    // DispatcherServlet 环境配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    // 拦截请求设置
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }

    /**
     * @param registration Servlet 上传文件配置
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // 配置上传文件路径
        /// TODO: 2019/4/24
        String filePath = "/mvc";
        // 5MB
        Long singleMax = (long) (1024*1024);
        // 1Gb
        Long totalMax = (long) (1024*1024*1024);
        // 设置上传文件配置
        registration.setMultipartConfig(new MultipartConfigElement(filePath,singleMax,totalMax,0));
    }
}
