package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ProductCategoryDetail;

@Repository
public interface ProductCategoryDetailRepository extends JpaRepository<ProductCategoryDetail, String> {

}