package com.example.cameltesttwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:camel-resources/applicationContext-camel.xml")
@SpringBootApplication
public class CamelTestTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelTestTwoApplication.class, args);
	}

}
