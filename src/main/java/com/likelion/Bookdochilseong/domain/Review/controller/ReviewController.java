package com.likelion.Bookdochilseong.domain.Review.controller;

import com.likelion.Bookdochilseong.domain.Review.dto.request.ReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.request.ReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.request.UpdateReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.response.ReviewResponseDto;
import com.likelion.Bookdochilseong.domain.Review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.createReview(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id,@RequestBody UpdateReviewRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.updateReview(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{readingStatusId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@PathVariable Long readingStatusId) {
        return ResponseEntity.ok(reviewService.getReviews(readingStatusId));
    }
}
