package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Service.ServiceClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private InventoryRepository inventoryRepository;

    // 3️⃣ Add Product
    @PostMapping
    public Map<String, Object> addProduct(@RequestBody Product product) {

        try {
            if (!serviceClass.validateProduct(product)) {
                return Map.of("message", "Product already exists");
            }

            Product saved = productRepository.save(product);
            return Map.of("products", saved);

        } catch (DataIntegrityViolationException e) {
            return Map.of("message", "SKU must be unique");
        }
    }

    // 4️⃣ Get Product by ID
    @GetMapping("/{id}")
    public Map<String, Object> getProductbyId(@PathVariable Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return Map.of("message", "Product not found");
        }

        return Map.of("products", product.get());
    }

    // 5️⃣ Update Product
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {

        productRepository.save(product);
        return Map.of("message", "Product updated successfully");
    }

    // 6️⃣ Filter by Name and Category
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterbyCategoryProduct(
            @PathVariable String name,
            @PathVariable String category) {

        List<Product> products;

        if ("null".equalsIgnoreCase(name) && "null".equalsIgnoreCase(category)) {
            products = productRepository.findAll();
        } else if ("null".equalsIgnoreCase(name)) {
            products = productRepository.findByCategory(category);
        } else if ("null".equalsIgnoreCase(category)) {
            products = productRepository.findAll()
                    .stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .toList();
        } else {
            products = productRepository.findByCategory(category)
                    .stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .toList();
        }

        return Map.of("products", products);
    }

    // 7️⃣ List All Products
    @GetMapping
    public Map<String, Object> listProduct() {

        List<Product> products = productRepository.findAll();
        return Map.of("products", products);
    }

    // 8️⃣ Filter by Category and StoreId
    @GetMapping("/filter/{category}/{storeid}")
    public Map<String, Object> getProductbyCategoryAndStoreId(
            @PathVariable String category,
            @PathVariable Long storeid) {

        List<Product> products = productRepository.findByNameLike(storeid, category);
        return Map.of("product", products);
    }

    // 9️⃣ Delete Product
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {

        if (!serviceClass.validateProductId(id)) {
            return Map.of("message", "Product not found");
        }

        inventoryRepository.deleteByProduct_Id(id);
        productRepository.deleteById(id);

        return Map.of("message", "Product deleted successfully");
    }

    // 🔟 Search Product by Name
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {

        List<Product> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        return Map.of("products", products);
    }
}