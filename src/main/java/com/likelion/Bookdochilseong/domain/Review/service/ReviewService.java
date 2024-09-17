package com.likelion.Bookdochilseong.domain.Review.service;

import com.likelion.Bookdochilseong.domain.Review.dto.request.ReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.request.UpdateReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    public ReviewResponseDto createReview(ReviewRequestDto requestDto);
    public ReviewResponseDto updateReview(Long reviewId, UpdateReviewRequestDto requestDto);
    public List<ReviewResponseDto> getReviews(Long readingStatusId);
    public void deleteReview(Long reviewId);

}
