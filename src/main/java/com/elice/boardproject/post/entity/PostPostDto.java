package com.elice.boardproject.post.entity;

import com.elice.boardproject.category.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostDto {
    private String postTitle;
    private String postContent;

    private Long categoryId;
}
