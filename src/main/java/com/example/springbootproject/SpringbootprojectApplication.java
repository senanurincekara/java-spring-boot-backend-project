package com.example.springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication //
@EntityScan(basePackages = {"com.example"}) // entiitleri görmesi için tanımlandı
@ComponentScan(basePackages = {"com.example"}) // rest controller , controller gibi anatasyonların beanlerini oluşması için tanımladık
@EnableJpaRepositories(basePackages = {"com.example"})
public class SpringbootprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}

}
