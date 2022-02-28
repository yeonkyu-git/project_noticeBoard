package project.noticeboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.noticeboard.config.interceptor.LoginCheckInterceptor;
import project.noticeboard.config.interceptor.LoginCheckInterceptor2;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/register", "/logout", "/css/*", "*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor2())
                .order(2)
                .addPathPatterns("/", "/register");
    }
}
