package com.project.code.Service;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.OrderItemRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 1️⃣ Save Order Method
    @Transactional
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {

        // 2️⃣ Retrieve or Create Customer
        Customer customer = customerRepository
                .findByEmail(placeOrderRequest.getEmail());

        if (customer == null) {
            customer = new Customer();
            customer.setName(placeOrderRequest.getCustomerName());
            customer.setEmail(placeOrderRequest.getEmail());
            customer.setPhone(placeOrderRequest.getPhone());
            customer = customerRepository.save(customer);
        }

        // 3️⃣ Retrieve Store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // 4️⃣ Create OrderDetails
        OrderDetails order = new OrderDetails();
        order.setCustomer(customer);
        order.setStore(store);
        order.setDate(LocalDateTime.now());

        double totalPrice = 0.0;

        order = orderDetailsRepository.save(order);

        // 5️⃣ Process Order Items
        for (OrderItemRequestDTO itemDTO : placeOrderRequest.getItems()) {

            Product product = productRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Inventory inventory = inventoryRepository
                    .findByProduct_IdAndStore_Id(product.getId(), store.getId());

            if (inventory == null || inventory.getStockLevel() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Deduct stock
            inventory.setStockLevel(
                    inventory.getStockLevel() - itemDTO.getQuantity()
            );
            inventoryRepository.save(inventory);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());

            totalPrice += product.getPrice() * itemDTO.getQuantity();

            orderItemRepository.save(orderItem);
        }

        // Update total price
        order.setTotalPrice(totalPrice);
        orderDetailsRepository.save(order);
    }
}