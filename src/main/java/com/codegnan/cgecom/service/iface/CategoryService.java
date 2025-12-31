package com.codegnan.cgecom.service.iface;

import java.util.List;

import com.codegnan.cgecom.model.Category;
import com.codegnan.cgecom.model.Product;
import com.codegnan.exception.DuplicateCategoryException;

public interface CategoryService {
    List<Category> findAll();
    Category findById(int id);
    Category save(Category category) throws DuplicateCategoryException;
    void deleteById(int id);
    Category getCategoryById(int id);
}
