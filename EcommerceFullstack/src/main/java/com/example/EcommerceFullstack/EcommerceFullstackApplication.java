package com.example.EcommerceFullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example.EcommerceFullstack")
@SpringBootApplication
public class EcommerceFullstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceFullstackApplication.class, args);
	}

}
