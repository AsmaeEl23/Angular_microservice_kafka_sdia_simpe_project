package org.sdia.orderserivice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.sdia.orderserivice.enums.OrderStatus;
import org.sdia.orderserivice.model.Customer;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;
    @Override
    public String toString() {
        return "order id "+id+"createdAt "+createdAt+" customer "+customer+" customerId "+ customerId+" product item "+productItems+" status "+status+" ";
    }
    public double getTotal(){
        double somme=0;
        for (ProductItem pi:productItems){
            somme+=pi.getAmount();
        }
        return somme;
    }
}
