package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.demo", "dto", "entity", "repository", "service", "com.example.demo.controller", "Config"})
@EnableJpaRepositories("repository")
@EntityScan("entity")
public class NeuraltransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeuraltransferApplication.class, args);
	}

}
