package de.wandi34.graalDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@GetMapping("/")
	public String showHelloWorld() {
		return "Hello World";
	}

}
