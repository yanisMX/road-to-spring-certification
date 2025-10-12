package com.example.revision_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RevisionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevisionAppApplication.class, args);
	}

}
