package com.codegnan.cgecom.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegnan.cgecom.model.Category;
import com.codegnan.cgecom.repositories.CategoryRepository;
import com.codegnan.cgecom.service.iface.CategoryService;
import com.codegnan.exception.DuplicateCategoryException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    
    @Override
    public Category getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new RuntimeException("Category not found with ID: " + id);
    }
    
    

    @Override
    public Category findById(int id) {
        Optional<Category> result = categoryRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    /*
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    */
    
    @Override
    public Category save(Category category) throws DuplicateCategoryException {
        Optional<Category> existing = categoryRepository.findByName(category.getName());

        // For new category (id=0) — prevent duplicates
        if (existing.isPresent() && category.getId() == 0) {
            throw new DuplicateCategoryException("Category with name '" + category.getName() + "' already exists.");
        }

        // For updating — allow same name only for the same category ID
        if (existing.isPresent() && existing.get().getId() != category.getId()) {
            throw new DuplicateCategoryException("Another category with the same name already exists.");
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}
