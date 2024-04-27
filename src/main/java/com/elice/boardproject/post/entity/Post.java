package com.elice.boardproject.post.entity;

import com.elice.boardproject.BaseEntity;
import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long postId;

    //private Long userId;
    private String postTitle;
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", fetch=FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments;

    public Post(String postTitle, String postContent, Category category){
        //this.userId = userId;
        this.category = category;
        this.postTitle = postTitle;
        this.postContent = postContent;
        comments = new ArrayList<>();
    }

    public PostPutDto toPostPutDto(){
        PostPutDto postPutDto = new PostPutDto();
        postPutDto.setPostId(this.postId);
        postPutDto.setPostTitle(this.postTitle);
        postPutDto.setPostContent(this.postContent);
        postPutDto.setComments(this.comments);
        postPutDto.setCategoryId(this.category.getCategoryId());
        return postPutDto;
    }
}
