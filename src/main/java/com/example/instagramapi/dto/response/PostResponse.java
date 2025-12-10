package com.example.instagramapi.dto.response;


import com.example.instagramapi.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private UserResponse author;

    private boolean liked;
    private long likeCount;
    private long commentCount;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .author(UserResponse.from(post.getUser())) //엔티티를 Dto 객체로 만들어서 저장하기 위해 여기 Get User을 살짝 바꿔준다.
                .createdAt(post.getCreatedAt())
                .liked(false)
                .likeCount(0)
                .commentCount(0)
                .build();

    }

    public static PostResponse from(
            Post post,
            boolean liked,
            long likeCount,
            long commentCount
            ) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .author(UserResponse.from(post.getUser())) //엔티티를 Dto 객체로 만들어서 저장하기 위해 여기 Get User을 살짝 바꿔준다.
                .createdAt(post.getCreatedAt())
                .liked(liked)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();

    }
}
