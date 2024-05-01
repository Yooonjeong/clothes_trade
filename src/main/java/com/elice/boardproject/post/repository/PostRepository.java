package com.elice.boardproject.post.repository;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.post.entity.ColorGroup;
import com.elice.boardproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByPostTitleContaining(String keyword, Pageable pageable);
    Page<Post> findByColor(ColorGroup color, Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.color = :colorGroup AND (p.postTitle LIKE %:keyword%)")
    Page<Post> findByKeywordAndColor(@Param("keyword") String keyword, @Param("color") ColorGroup color, Pageable pageable);
}
