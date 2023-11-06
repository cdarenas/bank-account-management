package com.xepelinbank.accountmanagement.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class XStreamConfig {
	@Bean
	XStream xStream() {
		XStream xStream = new XStream();
		xStream.addPermission(AnyTypePermission.ANY);

		return xStream;
	}
}
