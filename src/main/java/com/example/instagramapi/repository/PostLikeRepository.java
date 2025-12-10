package com.example.instagramapi.repository;

import com.example.instagramapi.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//오늘은 토글 형식이 아닌, delete 요청까지 처리한다. 이전에는 둘다 post로 했었다.
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    //좋아요 조회
    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);

    // 좋아요 여부 확인
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // 게시물의 좋아요 수
    long countByPostId(Long postId);




    //
}
