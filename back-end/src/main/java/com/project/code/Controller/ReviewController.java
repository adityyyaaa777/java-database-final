package com.project.code.Controller;

import com.project.code.Model.Review;
import com.project.code.Model.Customer;
import com.project.code.Repo.ReviewRepository;
import com.project.code.Repo.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // 3️⃣ Get Reviews By StoreId and ProductId
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(
            @PathVariable Long storeId,
            @PathVariable Long productId) {

        List<Review> reviews =
                reviewRepository.findByStoreIdAndProductId(storeId, productId);

        List<Map<String, Object>> filteredReviews = new ArrayList<>();

        for (Review review : reviews) {

            Optional<Customer> customer =
                    customerRepository.findById(review.getCustomerId());

            String customerName = customer.isPresent()
                    ? customer.get().getName()
                    : "Unknown";

            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("comment", review.getComment());
            reviewData.put("rating", review.getRating());
            reviewData.put("customerName", customerName);

            filteredReviews.add(reviewData);
        }

        return Map.of("reviews", filteredReviews);
    }
}