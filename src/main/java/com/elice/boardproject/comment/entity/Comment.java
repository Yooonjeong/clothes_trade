package com.elice.boardproject.comment.entity;

import com.elice.boardproject.BaseEntity;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    // Long userId;
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(Post post, String commentContent){
        this.post = post;
        this.commentContent = commentContent;
    }
//    public Comment(Post post, String commentContent, LocalDateTime createdDate, LocalDateTime modifiedDate){
//        this.post = post;
//        this.commentContent = commentContent;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
    public CommentPutDto toCommentPutDto(){
        CommentPutDto commentPutDto = new CommentPutDto();
        commentPutDto.setCommentId(this.commentId);
        commentPutDto.setPost(this.post);
        commentPutDto.setCommentContent(this.commentContent);
        return commentPutDto;
    }
}
