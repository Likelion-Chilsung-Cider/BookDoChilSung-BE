package com.likelion.Bookdochilseong.domain.Rating.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RatingResponseDto {
    private Long id;
    private Long readingStatusId;
    private int rating;

    @Builder
    public RatingResponseDto(Long id, Long readingStatusId, int rating) {
        this.id = id;
        this.readingStatusId = readingStatusId;
        this.rating = rating;
    }
}
