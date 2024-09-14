package com.likelion.Bookdochilseong.domain.Review.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private Long readingStatusId;
    private String comment;

    public ReviewResponseDto(Long id, Long readingStatusId, String comment) {
        this.id = id;
        this.readingStatusId = readingStatusId;
        this.comment = comment;
    }
}
