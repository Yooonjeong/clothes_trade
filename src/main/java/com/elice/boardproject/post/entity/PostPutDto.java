package com.elice.boardproject.post.entity;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.comment.entity.Comment;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPutDto {
    private Long postId;
    private String postTitle;
    private String postContent;

    private ColorGroup color;

    private Long categoryId;
    private List<Comment> comments;
}
