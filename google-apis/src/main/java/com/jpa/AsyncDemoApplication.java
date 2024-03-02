package com.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AsyncDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(com.demo.AsyncDemoApplication.class, args);
	}

	private static final String pod = UUID.randomUUID().toString();

	public String getPod() {
		return pod;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("AAAA: " + pod);
	}
}
