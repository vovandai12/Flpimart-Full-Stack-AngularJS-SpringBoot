package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ImageProduct;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, String> {

}