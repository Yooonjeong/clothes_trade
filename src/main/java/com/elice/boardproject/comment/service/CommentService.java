package com.elice.boardproject.comment.service;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.comment.entity.CommentPostDto;
import com.elice.boardproject.comment.entity.CommentPutDto;
import com.elice.boardproject.comment.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> retrieveCommentByPost(Post post){
        return commentRepository.findByPost(post);
    }
    public Comment retrieveCommentById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("Comment with id " + commentId + " does not exist"));
    }

    public Comment createComment(CommentPostDto commentPostDto){


        Comment comment = new Comment();
        comment.setCommentContent(commentPostDto.getCommentContent());
        comment.setPost(commentPostDto.getPost());

        Comment savedComment = commentRepository.save(comment);
        return savedComment;

    }

    public Comment updateComment(Long id, CommentPutDto commentPutDto){
        return commentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setCommentContent(commentPutDto.getCommentContent());
                    existingComment.setPost(commentPutDto.getPost());
                    Comment updatedPost = commentRepository.save(existingComment);
                    return updatedPost;
                })
                .orElseThrow(() -> new IllegalStateException("Comment with id " + id + " does not exist"));
    }

    public void deleteComment(Long id){
        commentRepository.findById(id).ifPresent(commentRepository::delete);
    }



}
