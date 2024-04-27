package com.elice.boardproject;

import com.elice.boardproject.category.entity.Category;
import com.elice.boardproject.category.repository.CategoryRepository;
import com.elice.boardproject.comment.repository.CommentRepository;
import com.elice.boardproject.post.entity.Post;
import com.elice.boardproject.post.repository.PostRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;


public class DataInit {
    private final CategoryRepository boardRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public DataInit(CategoryRepository boardRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        boardRepository.save(new Category("상의", "반팔/긴팔/나시/아우터/..."));
        boardRepository.save(new Category("하의", "반바지/긴바지/스커트/원피스/..."));
        boardRepository.save(new Category("신발", "스니커즈/구두/부츠/샌들/..."));
        boardRepository.save(new Category("가방", "백팩/토트백/숄더백/지갑/..."));
        boardRepository.save(new Category("모자", "캡/비니/버킷햇/베레모/..."));
        boardRepository.save(new Category("패션잡화", "안경/시계/주얼리/레그웨어/..."));
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
//        Category top = new Category("상의", "반팔/긴팔/나시/아우터/...");
//        Category bottom = new Category("하의", "반바지/긴바지/스커트/원피스/...");
//        boardRepository.save(top);
//        boardRepository.save(bottom);
//        postRepository.save(new Post("빨간색 가디건 팝니다", "미착용 제품입니다. 10000원", top));
//        postRepository.save(new Post("나이키 흰 바람막이 팝니다", "30000원에 팝니다.", top));
//    }

}