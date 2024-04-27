package com.elice.boardproject.category.repository;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Page<Post> findPostsByCategoryId(Long categoryId, Pageable pageable);
}
