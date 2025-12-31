package com.codegnan.cgecom.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codegnan.cgecom.model.Category;
import com.codegnan.cgecom.model.Product;
import com.codegnan.cgecom.model.User;
import com.codegnan.cgecom.service.iface.CategoryService;
import com.codegnan.cgecom.service.iface.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;

	// Inject the interface
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	
	/*
	// Existing method for listing products
	@GetMapping("/products")
	public String showProductList(HttpSession session, 
			 @RequestParam(defaultValue = "0") int page,  // Default page = 0
	            @RequestParam(defaultValue = "6") int size, // Default page size = 6
	            Model model) {
		// Check if the user is logged in
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			// If the user is not logged in, redirect to login page
			return "redirect:/login";
		}

		// If logged in, show the product list
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		
		
		
		Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getPaginatedProducts(pageable);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
		
		
		
		
		
		return "product-list"; // JSP file name
	}
	
	
	*/
	
	
	
	// Product List Page with search + pagination
    @GetMapping("/products")
    public String showProductList(HttpSession session,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "6") int size,
                                   @RequestParam(required = false) Integer categoryId,
                                   @RequestParam(required = false) String name,
                                   Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Get category list for dropdown
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // Fetch paginated + filtered products
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.searchProducts(categoryId, name, pageable);

        // Add data to model
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("name", name);

        return "product-list";
    }
	
	
	
	
	
	
	

	// New GET mapping for /products/add to ensure only admin can access

	@GetMapping("/products/add")
	public String showProducts(HttpSession session, Model model) {
		// Check if the user is logged in and if they are an admin

		System.out.println("in add products");
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
			// If not logged in or not an admin, redirect to login
			return "redirect:/login";
		}

		// If logged in and admin, load products and categories
		model.addAttribute("products", productService.getAllProducts());
		/*
		 * for(Category category:categoryService.findAll()) {
		 * System.out.println("in category list");
		 * System.out.println(category.getDescription()); }
		 */

		// System.out.println();
		model.addAttribute("categories", categoryService.findAll());
		return "add-product"; // name of the JSP
	}

	@PostMapping("/products/add")
	public String addNewProduct(@ModelAttribute Product product, @RequestParam("categoryId") int categoryId) {
		try {

			Category category = categoryService.getCategoryById(categoryId);

			// Save the uploaded image if it exists
			if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
				String imagePath = productService.saveProductImage(product.getImageFile());
				product.setImagePath(imagePath); // Set the saved image path to the product
			}
			System.out.println("category id:::::::::" + category.getDescription());
			// Save the product details (including the image path, if any)
			product.setCategory(category);
			productService.saveProduct(product);
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/products/add?error=FileUploadFailed"; // Redirect with error message
		}

		return "redirect:/products"; // Redirect to the product list
	}

	// New GET mapping for /products/edit/{id} to show the edit form
	@GetMapping("/products/edit/{id}")
	public String showEditProductPage(@PathVariable int id, HttpSession session, Model model) {
		// Check if the user is logged in and if they are an admin
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
			// If not logged in or not an admin, redirect to login
			return "redirect:/login";
		}

		// Fetch categories list from categoryService
		model.addAttribute("categories", categoryService.findAll());
		// Fetch the product from the database using the productService
		Product product = productService.getProductById(id);
		if (product == null) {
			return "redirect:/products?error=ProductNotFound";
		}

		// Add the product to the model to be used in the form
		model.addAttribute("product", product);
		return "edit-product"; // JSP file for editing a product
	}

	// POST mapping for updating a product
	@PostMapping("/products/edit/{id}")
	public String updateProduct(@PathVariable int id, @ModelAttribute Product product,
			@RequestParam("categoryId") int categoryId) {
		try {

			System.out.println("In save product@@@@@@@@@@@@@@@@@@@@");
			// Check if the product exists
			Product existingProduct = productService.getProductById(id);
			if (existingProduct == null) {
				return "redirect:/products?error=ProductNotFound";
			}

			Category category = categoryService.getCategoryById(categoryId);
			// Retrieve and set the category if the categoryId is present in the Product
			if (category != null) {
				product.setCategory(category);

			} else if (category == null) {
				return "redirect:/products/edit/" + id + "?error=CategoryNotFound";
			}

			// If a new image is uploaded, save it and update the image path
			if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
				String imagePath = productService.saveProductImage(product.getImageFile());
				product.setImagePath(imagePath);
			} else {
				// If no new image is uploaded, keep the old image path
				product.setImagePath(existingProduct.getImagePath());
			}

			// Set the ID of the existing product to update it
			product.setId(id);

			// Save the updated product
			productService.saveProduct(product);
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/products/edit/" + id + "?error=FileUploadFailed"; // Redirect with error message
		}

		return "redirect:/products"; // Redirect to the product list after updating
	}

	// DELETE mapping for removing a product (you can call this from a delete button
	// on the product list page)
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		return "redirect:/products"; // Redirect to the product list after deletion
	}

}
