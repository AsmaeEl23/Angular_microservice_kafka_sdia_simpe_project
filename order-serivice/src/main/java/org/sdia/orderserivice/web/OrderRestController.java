package org.sdia.orderserivice.web;

import org.sdia.orderserivice.entities.Order;
import org.sdia.orderserivice.model.Customer;
import org.sdia.orderserivice.model.Product;
import org.sdia.orderserivice.repository.OrderRepository;
import org.sdia.orderserivice.repository.ProductItemRepository;
import org.sdia.orderserivice.service.CustomerRestClientService;
import org.sdia.orderserivice.service.InventoryRestClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestClientService customerRestClientService, InventoryRestClientService inventoryRestClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        System.out.println("Order "+order);
        Customer customer=customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(productItem -> {
            Product product=inventoryRestClientService.productById(productItem.getProductId());
            productItem.setProduct(product);
                }
        );
        return order;
    }

}
