package com.example.instagramapi.controller;


import com.example.instagramapi.dto.request.CommentCreateRequest;
import com.example.instagramapi.dto.request.PostCreateRequest;
import com.example.instagramapi.dto.response.ApiResponse;
import com.example.instagramapi.dto.response.CommentResponse;
import com.example.instagramapi.dto.response.LikeResponse;
import com.example.instagramapi.dto.response.PostResponse;
import com.example.instagramapi.entity.Post;
import com.example.instagramapi.security.CustomUserDetails;
import com.example.instagramapi.service.CommentService;
import com.example.instagramapi.service.PostLikeService;
import com.example.instagramapi.service.PostService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostResponse response = postService.create(userDetails.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        List<PostResponse> posts = postService.findAll(userId);
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        postService.delete(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.create(id, userDetails.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    //댓글 출력
    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long id
    ) {
        List<CommentResponse> responses = commentService.findByPostId(id);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    //댓글 삭제

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        commentService.delete(commentId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    //좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<ApiResponse<LikeResponse>> Like(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        LikeResponse response = postLikeService.like(userDetails.getId(), id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    //delete인데 like로 통일!
    @DeleteMapping("/{id}/like")
    public ResponseEntity<ApiResponse<LikeResponse>> UnLike(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        LikeResponse response = postLikeService.unlike(userDetails.getId(), id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<ApiResponse<LikeResponse>>  getLikeStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        LikeResponse response = postLikeService.getLikedStatus(id, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}