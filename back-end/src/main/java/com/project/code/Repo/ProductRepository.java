package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2. Retrieve all products (already available in JpaRepository,
    // but explicitly written as per instruction)
    List<Product> findAll();

    // Retrieve products by category
    List<Product> findByCategory(String category);

    // Retrieve products within price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Retrieve product by SKU
    Product findBySku(String sku);

    // Retrieve product by name
    Product findByName(String name);

    // Retrieve products by name pattern for a specific store
    @Query("SELECT p FROM Product p " +
           "JOIN Inventory i ON p.id = i.product.id " +
           "WHERE i.store.id = :storeId " +
           "AND p.name LIKE %:pname%")
    List<Product> findByNameLike(@Param("storeId") Long storeId,
                                 @Param("pname") String pname);
}