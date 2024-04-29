package com.elice.boardproject.category.service;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.entity.CategoryPostDto;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.category.repository.CategoryRepository;
import com.elice.boardproject.post.entity.Post;
import com.elice.boardproject.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, PostRepository postRepository){
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public List<Category> retrieveAllCategories(){
        return categoryRepository.findAll();
    }

    public Category retrieveCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category with id " + id + " does not exist"));
    }


    public Category createCategory(CategoryPostDto categoryPostDto){
        Category category = new Category();
        category.setCategoryName(categoryPostDto.getCategoryName());
        category.setCategoryDescription(categoryPostDto.getCategoryDescription());
        category.setPosts(new ArrayList<>());
        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
    }

    public Category updateCategory(Long id, CategoryPutDto categoryPutDto){
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(categoryPutDto.getCategoryName());
                    existingCategory.setCategoryDescription(categoryPutDto.getCategoryDescription());
                    existingCategory.setPosts(categoryPutDto.getPosts());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return updatedCategory;
                })
                .orElseThrow(() -> new IllegalStateException("Category with id " + id + " does not exist"));
    }

    public Category addPost(Post post, CategoryPutDto categoryPutDto){
        Category category = categoryRepository.findById(categoryPutDto.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category with id " + categoryPutDto.getCategoryId() + " does not exist"));
        if (!category.getPosts().contains(post)) {
            category.getPosts().add(post);
        }

        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
    public void deleteCategory(Long id){
//        Optional<Category> optionalCategory = categoryRepository.findById(id);
//        if(optionalCategory.isPresent()){
//            Category category = optionalCategory.get();
//            List<Post> posts = category.getPosts();
//            for(Post post:posts){
//                postRepository.delete(post);
//            }
//        }
        categoryRepository.findById(id).ifPresent(category -> categoryRepository.delete(category));
    }

//    public Page<Post> findPostsByCategoryId(Long categoryId, Pageable pageable){
//        Optional<Category> category =  categoryRepository.findById(categoryId);
//        if(category.isPresent()){
//
//        }
//
//    }

}

