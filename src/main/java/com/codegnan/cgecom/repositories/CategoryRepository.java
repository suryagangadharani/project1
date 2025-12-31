package com.codegnan.cgecom.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.cgecom.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);  
}
