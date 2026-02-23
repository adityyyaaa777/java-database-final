package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 2. Retrieve Store by ID (already available in JpaRepository)
    Optional<Store> findById(Long id);

    // Retrieve stores whose name contains a given substring
    @Query("SELECT s FROM Store s WHERE s.name LIKE %:pname%")
    List<Store> findBySubName(@Param("pname") String pname);
}