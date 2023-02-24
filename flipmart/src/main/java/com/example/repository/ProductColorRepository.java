package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ProductColor;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, String> {

}