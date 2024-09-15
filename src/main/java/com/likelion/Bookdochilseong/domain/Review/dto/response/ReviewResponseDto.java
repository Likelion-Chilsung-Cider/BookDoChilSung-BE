package com.likelion.Bookdochilseong.domain.Review.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private Long readingStatusId;
    private String content;

    public ReviewResponseDto(Long id, Long readingStatusId, String content) {
        this.id = id;
        this.readingStatusId = readingStatusId;
        this.content = content;
    }
}
