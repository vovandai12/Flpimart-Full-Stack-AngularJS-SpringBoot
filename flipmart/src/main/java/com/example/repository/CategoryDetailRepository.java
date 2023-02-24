package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Category;
import com.example.model.CategoryDetail;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, String> {

	List<CategoryDetail> findAllByCategory (Category category);
}