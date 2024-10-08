package com.elice.boardproject.post.service;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.entity.CategoryPostDto;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.category.repository.CategoryRepository;
import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.post.entity.ColorGroup;
import com.elice.boardproject.post.entity.Post;
import com.elice.boardproject.post.entity.PostPostDto;
import com.elice.boardproject.post.entity.PostPutDto;
import com.elice.boardproject.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository){
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Post> retrieveAllPosts(){
        return postRepository.findAll();
    }

    public Page<Post> retrieveAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post retrievePostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post with id " + id + " does not exist"));
    }

    public Page<Post> searchPostsByCategory(Category category, Pageable pageable){
        return postRepository.findByCategory(category, pageable);
    }

    public Page<Post> searchPostsByKeyword(String keyword, Pageable pageable){
        return postRepository.findByPostTitleContaining(keyword, pageable);
    }

    public Page<Post> searchPostsByColor(ColorGroup color, Pageable pageable){
        return postRepository.findByColor(color, pageable);
    }

    public Page<Post> searchPostsByKeywordAndColor(String keyword, ColorGroup color, Pageable pageable){
        return postRepository.findByKeywordAndColor(keyword, color, pageable);
    }

    public Post createPost(PostPostDto postPostDto){

        Category category = categoryRepository.findById(postPostDto.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category with id " + postPostDto.getCategoryId() + " does not exist"));

        Post post = new Post();
        post.setPostTitle(postPostDto.getPostTitle());
        post.setPostContent(postPostDto.getPostContent());
        post.setCategory(category);
        post.setComments(new ArrayList<>());
        post.setColor(postPostDto.getColor());

        Post savedPost = postRepository.save(post);
        return savedPost;

    }

    public Post createPost(PostPostDto postPostDto, String image){

        Category category = categoryRepository.findById(postPostDto.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category with id " + postPostDto.getCategoryId() + " does not exist"));

        Post post = new Post();
        post.setPostTitle(postPostDto.getPostTitle());
        post.setPostContent(postPostDto.getPostContent());
        post.setCategory(category);
        post.setComments(new ArrayList<>());
        post.setColor(postPostDto.getColor());
        post.setImage(image);

        Post savedPost = postRepository.save(post);
        return savedPost;

    }

    public Post updatePost(Long id, PostPutDto postPutDto, String image){
        Category category = categoryRepository.findById(postPutDto.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category with id " + postPutDto.getCategoryId() + " does not exist"));

        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setPostTitle(postPutDto.getPostTitle());
                    existingPost.setPostContent(postPutDto.getPostContent());
                    existingPost.setCategory(category);
                    existingPost.getComments().clear();
                    if(postPutDto.getComments() != null) {
                        existingPost.getComments().addAll(postPutDto.getComments());
                    }
                    existingPost.setColor(postPutDto.getColor());
                    existingPost.setImage(image);
                    Post updatedPost = postRepository.save(existingPost);
                    return updatedPost;
                })
                .orElseThrow(() -> new IllegalStateException("Post with id " + id + " does not exist"));
    }

    public void deletePost(Long id){
        postRepository.findById(id).ifPresent(post -> postRepository.delete(post));
    }

    public Post addComment(Comment comment, PostPutDto postPutDto){
        Post post = postRepository.findById(postPutDto.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post with id " + postPutDto.getPostId() + " does not exist"));

        if(!post.getComments().contains(comment)){
            post.getComments().add(comment);
        }

        Post savedPost = postRepository.save(post);
        return savedPost;

    }
}
