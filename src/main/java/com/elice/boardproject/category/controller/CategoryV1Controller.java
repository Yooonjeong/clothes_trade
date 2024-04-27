package com.elice.boardproject.category.controller;

import com.elice.boardproject.category.entity.CategoryPostDto;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.category.entity.CategoryResponseDto;
import com.elice.boardproject.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/categories")
//public class CategoryV1Controller {
//    private final CategoryService categoryService;
//
//    @Autowired
//    public CategoryV1Controller(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @GetMapping
//    public List<CategoryResponseDto> getCategories(){
//        return categoryService.retrieveAllCategories();
//    }
//
//    @GetMapping("/{id}")
//    public CategoryResponseDto getCategoryById(@PathVariable long id) {
//        return categoryService.retrieveCategoryById(id);
//    }
//
//    @PostMapping
//    public CategoryResponseDto postCategory(@RequestBody CategoryPostDto categoryPostDto) {
//        return categoryService.createCategory(categoryPostDto);
//    }
//
//    @PutMapping("/{id}")
//    public CategoryResponseDto putCategory(@PathVariable long id, @RequestBody CategoryPutDto categoryPutDto) {
//        return categoryService.updateCategory(id, categoryPutDto);
//    }
//
//}
