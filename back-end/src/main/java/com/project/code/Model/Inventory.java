package com.project.code.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "inventory")
public class Inventory {

    // 1. Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Product Relationship
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-product")
    private Product product;

    // 3. Store Relationship
    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("inventory-store")
    private Store store;

    // 4. Stock Level
    private Integer stockLevel;

    // 7. Default Constructor
    public Inventory() {
    }

    // Parameterized Constructor
    public Inventory(Product product, Store store, Integer stockLevel) {
        this.product = product;
        this.store = store;
        this.stockLevel = stockLevel;
    }

    // 9. Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
}