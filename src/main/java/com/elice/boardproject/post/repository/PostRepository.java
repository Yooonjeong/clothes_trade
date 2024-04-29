package com.elice.boardproject.post.repository;

import com.elice.boardproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostTitleContaining(String keyword);
}
