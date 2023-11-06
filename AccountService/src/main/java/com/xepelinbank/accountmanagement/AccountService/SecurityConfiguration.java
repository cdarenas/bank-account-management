package com.xepelinbank.accountmanagement.AccountService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name());
	}

}
