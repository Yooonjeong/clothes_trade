package com.elice.boardproject.comment.repository;

import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post);
}
