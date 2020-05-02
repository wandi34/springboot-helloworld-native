package de.wandi34.graalDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class GraalDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraalDemoApplication.class, args);
	}
}
