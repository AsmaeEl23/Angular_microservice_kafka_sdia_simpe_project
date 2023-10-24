package org.sdia.orderserivice;

import org.sdia.orderserivice.entities.Order;
import org.sdia.orderserivice.entities.ProductItem;
import org.sdia.orderserivice.enums.OrderStatus;
import org.sdia.orderserivice.model.Customer;
import org.sdia.orderserivice.model.Product;
import org.sdia.orderserivice.repository.OrderRepository;
import org.sdia.orderserivice.repository.ProductItemRepository;
import org.sdia.orderserivice.service.CustomerRestClientService;
import org.sdia.orderserivice.service.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderSeriviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSeriviceApplication.class, args);
    }
@Bean
    CommandLineRunner start(OrderRepository orderRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClientService customerRestClientService,
                            InventoryRestClientService inventoryRestClientService){
        return args -> {
            List<Customer> customers=customerRestClientService.allCustomers().getContent().stream().toList();
            List<Product> products=inventoryRestClientService.allProducts().getContent().stream().toList();
            Long customerId=1L;
            Customer customer=customerRestClientService.customerById(customerId);
            Random random=new Random();
            for(int i=0;i<20;i++){
                Order order=Order.builder()
                        .createdAt(new Date())
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
                        .build();
                Order saveOrder=orderRepository.save(order);
                for(int j=0;j<products.size();j++){
                    if (Math.random()>0.70){
                        ProductItem productItem=ProductItem.builder()
                                .order(saveOrder)
                                .productId(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .quantity(1+random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }
                }
            }
        };
}
}
