package com.elice.boardproject.category.entity;

import com.elice.boardproject.post.entity.Post;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;

    private List<Post> posts;

    public CategoryResponseDto(Category category){
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryDescription = category.getCategoryDescription();
        if(category.getPosts() != null){
            this.posts = category.getPosts();
        }
    }

}
