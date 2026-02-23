package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    // 1. Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Name Field
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;

    // 3. Email Field
    @NotNull(message = "Email cannot be null")
    @Column(nullable = false)
    private String email;

    // 4. Phone Field
    @NotNull(message = "Phone cannot be null")
    @Column(nullable = false)
    private String phone;

    // 5. One-to-Many Relationship with Order
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetails> orders;

    // 6. Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}