package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 2. Find Inventory by Product ID and Store ID
    Inventory findByProduct_IdAndStore_Id(Long productId, Long storeId);

    // 2. Find all Inventory records by Store ID
    List<Inventory> findByStore_Id(Long storeId);

    // 2. Delete Inventory records by Product ID
    @Modifying
    @Transactional
    void deleteByProduct_Id(Long productId);
}