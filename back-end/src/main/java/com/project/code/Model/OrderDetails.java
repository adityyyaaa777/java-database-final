package com.project.code.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderDetails {

    // 1. Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Customer Relationship
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    // 3. Store Relationship
    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private Store store;

    // 4. Total Price
    private Double totalPrice;

    // 5. Order Date
    private LocalDateTime date;

    // 6. One-to-Many Relationship with OrderItem
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    // 7. Constructors

    // No-Argument Constructor
    public OrderDetails() {
    }

    // Parameterized Constructor
    public OrderDetails(Customer customer, Store store, Double totalPrice, LocalDateTime date) {
        this.customer = customer;
        this.store = store;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // 9. Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}