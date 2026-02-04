package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("==============================================");
		System.out.println("🩸 Blood Donor Finder Application Started! 🩸");
		System.out.println("==============================================");
		System.out.println("Server: http://localhost:8080");
		System.out.println("H2 Console: http://localhost:8080/h2-console");
		System.out.println("API Docs: http://localhost:8080/api/donors");
		System.out.println("==============================================");
	}

}
