package com.elice.boardproject.category.entity;


import com.elice.boardproject.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch=FetchType.EAGER)
    private List<Post> posts;


    public Category(String categoryName, String categoryDescription){
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.posts = new ArrayList<>();
    }

    public Category(String categoryName, String categoryDescription, List<Post> posts){
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.posts = posts;
    }

    public CategoryPutDto toCategoryPutDto(){
        CategoryPutDto categoryPutDto = new CategoryPutDto();
        categoryPutDto.setCategoryId(this.categoryId);
        categoryPutDto.setCategoryName(this.categoryName);
        categoryPutDto.setCategoryDescription(this.categoryDescription);
        categoryPutDto.setPosts(this.getPosts());
        return categoryPutDto;
    }


}
