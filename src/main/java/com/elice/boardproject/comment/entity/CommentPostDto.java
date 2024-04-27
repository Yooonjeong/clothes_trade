package com.elice.boardproject.comment.entity;

import com.elice.boardproject.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostDto {

    private String commentContent;
    private Post post;
}
