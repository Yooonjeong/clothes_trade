package com.elice.boardproject.post.entity;

import com.elice.boardproject.category.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostDto {
    private String postTitle;
    private String postContent;

    private ColorGroup color;
    private MultipartFile image;

    private Long categoryId;
}
