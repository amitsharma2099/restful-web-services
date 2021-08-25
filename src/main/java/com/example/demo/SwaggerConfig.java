//package com.example.demo;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.VendorExtension;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger2 provides JSON API documentation for spring based applications. Swagger2 is an open source project used to generate the REST API documents 
// * for RESTful web services. It provides a user interface to access our RESTful web services via the web browser
// * 
// * Either we can have separate configuration class as this OR put these configuration in main Spring Boot application class.
// * 
// * @author Amit, 10-Jul-2019
// */
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	//These constants have been copied from ApiInfo class
//	public static final Contact DEFAULT_CONTACT = new Contact("Vijay Garg", "www.example.url.com", "vgarg@gmail.com");
//	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome Api Title", "Awesome Api Description", "1.0", "urn:tos",
//	          DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
//	
//	private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json", "application/xml"));
//
////	http://localhost:8080/swagger-ui.html
////	http://localhost:8080/swagger-resources
////	http://localhost:8080/v2/api-docs
//	@Bean
//	public Docket api() {
////		return new Docket(DocumentationType.SWAGGER_2);
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO)
//													  .produces(DEFAULT_PRODUCES_CONSUMES)
//													  .consumes(DEFAULT_PRODUCES_CONSUMES);
//		
////		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.demo")).build();
//	}
//}
