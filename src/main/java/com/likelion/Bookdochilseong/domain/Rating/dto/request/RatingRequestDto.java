package com.likelion.Bookdochilseong.domain.Rating.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDto {
    private Long bookId;
    private int rating;
}

