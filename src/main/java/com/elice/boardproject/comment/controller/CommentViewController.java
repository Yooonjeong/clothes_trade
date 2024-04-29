package com.elice.boardproject.comment.controller;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.entity.CategoryPutDto;
import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.comment.entity.CommentPostDto;
import com.elice.boardproject.comment.entity.CommentPutDto;
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
@RequestMapping("/comments")
public class CommentViewController {
    private final CommentService commentService;
    private final PostService postService;

    public CommentViewController(CommentService commentService, PostService postService){
        this.commentService = commentService;
        this.postService = postService;
    }


    @Transactional
    @PostMapping
    public String createComment(@ModelAttribute CommentPostDto commentPostDto, RedirectAttributes redirectAttributes, @RequestParam("postId") Long postId){
        Post post = postService.retrievePostById(postId);

        commentPostDto.setPost(post);
        Comment comment = commentService.createComment(commentPostDto);
        PostPutDto postPutDto = post.toPostPutDto();

        Post updatedPost = postService.addComment(comment, postPutDto);

        List<Comment> comments = updatedPost.getComments();

        redirectAttributes.addFlashAttribute("post", post);
        redirectAttributes.addFlashAttribute("comments", comments);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("{commentId}/edit")
    public String editComment(@ModelAttribute CommentPutDto commentPutDto, RedirectAttributes redirectAttributes, @PathVariable Long commentId, @RequestParam("postId") Long postId){

        Post post = postService.retrievePostById(postId);
        commentPutDto.setPost(post);
        commentPutDto.setCommentId(commentId);

        Comment updatedComment = commentService.updateComment(commentId, commentPutDto);

        List<Comment> comments = post.getComments();
        redirectAttributes.addFlashAttribute("post", post);
        redirectAttributes.addFlashAttribute("comments", comments);

        return "redirect:/posts/" + postId;
    }

    @Transactional
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId, RedirectAttributes redirectAttributes){
        Comment comment = commentService.retrievePostById(commentId);
        Post post = comment.getPost();
        post.getComments().remove(comment);
        List<Comment> comments =post.getComments();

        commentService.deleteComment(commentId);

        redirectAttributes.addFlashAttribute("post", post);
        redirectAttributes.addFlashAttribute("comments", comments);
        return "redirect:/posts/"+ post.getPostId();
    }
}
