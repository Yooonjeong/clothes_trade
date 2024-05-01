package com.elice.boardproject.post.controller;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.category.service.CategoryService;
import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.comment.service.CommentService;
import com.elice.boardproject.post.entity.Post;
import com.elice.boardproject.post.entity.PostPostDto;
import com.elice.boardproject.post.entity.PostPutDto;
import com.elice.boardproject.post.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostViewController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    public PostViewController(PostService postService, CategoryService categoryService, CommentService commentService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

//    @GetMapping
//    public String getPosts(Model model, @RequestParam("categoryId") Long categoryId, RedirectAttributes redirectAttributes){
//        Category category = categoryService.retrieveCategoryById(categoryId);
//        //model.addAttribute("posts", posts);
//        //다시 수정
//        //return "category/category";
//        redirectAttributes.addFlashAttribute("category", category);
//        List<Post> posts = category.getPosts();
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());
//
//        redirectAttributes.addFlashAttribute("postPage", postPage);
//        return "redirect:/categories/" + categoryId;
//    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model){
        Post post = postService.retrievePostById(postId);
        List<Comment> comments = post.getComments();
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post/post";
    }

    @GetMapping("/create")
    public String createPostForm(Model model, @RequestParam("categoryId") Long categoryId) {
        model.addAttribute("categoryId", categoryId);
        return "post/createPost";
    }

    @Transactional
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostPostDto postPostDto, RedirectAttributes redirectAttributes, @RequestParam("categoryId") Long categoryId){
        Post createdPost = postService.createPost(postPostDto);
        Category category = createdPost.getCategory();
        CategoryPutDto categoryPutDto = category.toCategoryPutDto();

        Category updatedCategory = categoryService.addPost(createdPost, categoryPutDto);

        redirectAttributes.addFlashAttribute("category", updatedCategory);
        List<Post> posts = updatedCategory.getPosts();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        redirectAttributes.addFlashAttribute("postPage", postPage);


        return "redirect:/categories/" + categoryId;
    }

    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model){
        Post post = postService.retrievePostById(postId);
        model.addAttribute("post", post);
        return "post/editPost";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@ModelAttribute PostPostDto postPostDto, @PathVariable Long postId, RedirectAttributes redirectAttributes, @RequestParam("categoryId") Long categoryId){

        Post post = postService.retrievePostById(postId);

        PostPutDto postPutDto = new PostPutDto();
        postPutDto.setPostTitle(postPostDto.getPostTitle());
        postPutDto.setPostContent(postPostDto.getPostContent());
        postPutDto.setPostId(postId);
        postPutDto.setComments(post.getComments());
        postPutDto.setCategoryId(categoryId);
        postPutDto.setColor(postPostDto.getColor());

        Post updatedPost = postService.updatePost(postId, postPutDto);
        Category category = updatedPost.getCategory();
        redirectAttributes.addFlashAttribute("category", category);

        List<Post> posts = category.getPosts();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());
        redirectAttributes.addFlashAttribute("postPage", postPage);
        return "redirect:/posts/"+ postId;
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId){
        Long categoryId = postService.retrievePostById(postId).getCategory().getCategoryId();
        Post post = postService.retrievePostById(postId);
        List<Comment> comments = post.getComments();
        for(Comment comment:comments){
            commentService.deleteComment(comment.getCommentId());
        }
        postService.deletePost(postId);
        return "redirect:/categories/" + categoryId;
    }

}
