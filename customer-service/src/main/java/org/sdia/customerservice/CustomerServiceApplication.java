package org.sdia.customerservice;

import org.sdia.customerservice.entities.Customer;
import org.sdia.customerservice.repo.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
			customerRepository.saveAll(List.of(
					Customer.builder().name("Asmae").email("asmaeee@gmail.com").build(),
					Customer.builder().name("Issam").email("isam@gmail.com").build(),
					Customer.builder().name("Fatima").email("fati.123@gmail.com").build(),
					Customer.builder().name("Anwar").email("anwarel@gmail.com").build()
			));
			customerRepository.findAll().forEach(System.out::println);
		};
	}
}
