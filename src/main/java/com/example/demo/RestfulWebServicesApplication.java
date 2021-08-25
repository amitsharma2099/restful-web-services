package com.example.demo;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 'spring-data-rest-hal-browser' dependency is used to show & invoke all services in browser. To open HAL dashboard, just type 
 * 'localhost:8080', it will lead to HAL browser URL 'http://localhost:8080/browser/index.html#/'
 * http://localhost:8080/actuator/metrics
 * http://localhost:8080/actuator/metrics/jvm.memory.used
 * http://localhost:8080/actuator/metrics/process.cpu.usage
 * 
 * @author Amit, 10-Jul-2019
 */
@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		//1. SessionLocaleResolver works when we accept locale from request header named Accept-Language as method argument in controller method
//		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		//2. otherwise, since we are passing Accept-Language as request header, and using LocaleContextHolder in controller method, we can use AcceptHeaderLocaleResolver as follows:
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	
	//We can define message-source base name in application.properties file. So following method can be removed.
	/*@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}*/
	
	
}
