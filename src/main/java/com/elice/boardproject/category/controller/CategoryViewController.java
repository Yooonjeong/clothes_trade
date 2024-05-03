package com.elice.boardproject.category.controller;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.entity.CategoryPostDto;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.comment.service.CommentService;
import com.elice.boardproject.post.entity.ColorGroup;
import com.elice.boardproject.post.entity.Post;
import com.elice.boardproject.post.entity.PostPostDto;
import com.elice.boardproject.post.entity.PostPutDto;
import com.elice.boardproject.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import com.elice.boardproject.category.entity.CategoryResponseDto;
import com.elice.boardproject.category.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryViewController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final CommentService commentService;

    public CategoryViewController(CategoryService categoryService, PostService postService, CommentService commentService){
        this.categoryService = categoryService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getCategories(Model model){
        List<Category> categories = categoryService.retrieveAllCategories();
        model.addAttribute("categories", categories);
        return "category/categories";
    }

    @GetMapping("/{categoryId}")
    public String getCategory(@PathVariable Long categoryId, @RequestParam(required = false) String keyword,
                              @RequestParam(required = false) ColorGroup color, @PageableDefault(size = 5) Pageable pageable, Model model){
        List<Category> categories = categoryService.retrieveAllCategories();
        model.addAttribute("categories", categories);

        Category category = categoryService.retrieveCategoryById(categoryId);
        model.addAttribute("category", category);


        Page<Post> posts;
        if ((keyword == null || keyword.trim().isEmpty()) && color == null) {
            posts = postService.searchPostsByCategory(category, pageable);
        }
        else if(color == null) {
            posts = postService.searchPostsByKeyword(keyword, pageable);
        }
        else if(keyword == null || keyword.trim().isEmpty()){
            posts = postService.searchPostsByColor(color, pageable);
        }
        else{
            posts = postService.searchPostsByKeywordAndColor(keyword, color, pageable);
        }

        model.addAttribute("postPage", posts);
        return "category/category";
    }

    @GetMapping("/create")
    public String createCategoryForm(@ModelAttribute CategoryPostDto categoryPostDto){
        return "category/createCategory";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryPostDto categoryPostDto, RedirectAttributes redirectAttributes){

        Category createdCategory = categoryService.createCategory(categoryPostDto);
        redirectAttributes.addAttribute("categoryId", createdCategory.getCategoryId());

        return "redirect:/categories";
    }

    @GetMapping("/{categoryId}/edit")
    public String editCategoryForm(@PathVariable Long categoryId, Model model){
        Category category = categoryService.retrieveCategoryById(categoryId);
        model.addAttribute("category", category);
        return "category/editCategory";
    }

    @PostMapping("/{categoryId}/edit")
    public String editCategory(@ModelAttribute CategoryPutDto categoryPutDto, @PathVariable Long categoryId, RedirectAttributes redirectAttributes){

        Category category = categoryService.retrieveCategoryById(categoryId);
        categoryPutDto.setCategoryId(categoryId);
        categoryPutDto.setPosts(category.getPosts());
        categoryService.updateCategory(categoryId, categoryPutDto);

        List<Category> categories = categoryService.retrieveAllCategories();
        redirectAttributes.addFlashAttribute("categories", categories);
        return "redirect:/categories";
    }

    @DeleteMapping("/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes){
        Category category = categoryService.retrieveCategoryById(categoryId);
        List<Post> posts = category.getPosts();
        List<Comment> comments = new ArrayList<>();
        for(Post post:posts){
            comments.addAll(post.getComments());
        }
        for(Comment comment:comments){
            commentService.deleteComment(comment.getCommentId());
        }
        for(Post post:posts){
            postService.deletePost(post.getPostId());
        }
        categoryService.deleteCategory(categoryId);

        List<Category> categories = categoryService.retrieveAllCategories();
        redirectAttributes.addFlashAttribute("categories", categories);
        return "redirect:/categories";
    }

}
