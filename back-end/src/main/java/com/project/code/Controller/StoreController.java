package com.project.code.Controller;

import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.Model.PlaceOrderRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // 3️⃣ Add Store
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {

        storeRepository.save(store);
        return Map.of("message", "Store created successfully");
    }

    // 4️⃣ Validate Store
    @GetMapping("/validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {

        return storeRepository.existsById(storeId);
    }

    // 5️⃣ Place Order
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(
            @RequestBody PlaceOrderRequestDTO request) {

        try {
            orderService.saveOrder(request);
            return Map.of("message", "Order placed successfully");
        } catch (Exception e) {
            return Map.of("Error", e.getMessage());
        }
    }
}