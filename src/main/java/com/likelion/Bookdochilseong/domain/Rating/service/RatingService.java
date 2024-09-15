package com.likelion.Bookdochilseong.domain.Rating.service;

import com.likelion.Bookdochilseong.domain.Rating.dto.request.RatingRequestDto;
import com.likelion.Bookdochilseong.domain.Rating.dto.response.RatingResponseDto;

public interface RatingService {

    public RatingResponseDto create(RatingRequestDto requestDto);
    public RatingResponseDto update(Long id , RatingRequestDto requestDto);
    public void delete(Long id);
    public RatingResponseDto get(Long id);
}
