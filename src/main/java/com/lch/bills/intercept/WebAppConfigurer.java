package com.lch.bills.intercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截配置--调用链
 * 创建者  科帮网 
 * 创建时间  2017年11月24日
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] patterns = new String[] { "/login","/*.html","/swagger-resources/**"};
		registry.addInterceptor(new LoginIntercept())
		                         .addPathPatterns("/**")
		                         .excludePathPatterns(patterns);
		super.addInterceptors(registry);
	}

}