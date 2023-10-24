package org.sdia.inventoryservice;

import org.sdia.inventoryservice.entities.Product;
import org.sdia.inventoryservice.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.saveAll(List.of(
                    Product.builder().name("HP").price(345900).quantity(500).build(),
                    Product.builder().name("Redmi").price(5900).quantity(5).build(),
                    Product.builder().name("USB").price(90).quantity(100).build(),
                    Product.builder().name("earphones").price(100).quantity(70).build()
            ));
            productRepository.findAll().forEach(System.out::println);
        };
    }
}
