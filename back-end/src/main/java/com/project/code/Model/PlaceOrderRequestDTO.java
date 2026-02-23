package com.project.code.Model;


import java.util.List;

public class PlaceOrderRequestDTO {
    private Long storeId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String date;
    private List<OrderItemRequestDTO> purchaseProduct;
    private Double totalPrice;

    // Getters and Setters

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return customerEmail;
    }

    public void setEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPhone() {
        return customerPhone;
    }

    public void setPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderItemRequestDTO> getItems() {
        return purchaseProduct;
    }

    public void setItems(List<OrderItemRequestDTO> purchaseProduct) {
        this.purchaseProduct = purchaseProduct;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
