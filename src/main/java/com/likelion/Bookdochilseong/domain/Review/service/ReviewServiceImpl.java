package com.likelion.Bookdochilseong.domain.Review.service;

import com.likelion.Bookdochilseong.domain.ReadingStatus.repository.ReadingStatusRepository;
import com.likelion.Bookdochilseong.domain.Review.dto.request.ReviewRequestDto;
import com.likelion.Bookdochilseong.domain.Review.dto.response.ReviewResponseDto;
import com.likelion.Bookdochilseong.domain.Review.repository.ReviewRepository;
import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import com.likelion.Bookdochilseong.entity.TblReview;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReadingStatusRepository readingStatusRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        TblReadingStatus readingStatus = readingStatusRepository.findById(requestDto.getReadingStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reading status ID"));

        TblReview review = TblReview.builder()
                .tblReadingStatus(readingStatus)
                .comment(requestDto.getContent())
                .build();

        TblReview savedReview = reviewRepository.save(review);
        return new ReviewResponseDto(savedReview.getId(), savedReview.getTblReadingStatus().getId(), savedReview.getComment());
    }

    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto) {
        TblReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        review.updateComment(requestDto.getContent());
        return new ReviewResponseDto(review.getId(), review.getTblReadingStatus().getId(), review.getComment());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewResponseDto> getReviews(Long readingStatusId) {
        return reviewRepository.findByTblReadingStatusId(readingStatusId).stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getTblReadingStatus().getId(), review.getComment()))
                .collect(Collectors.toList());
    }
}
