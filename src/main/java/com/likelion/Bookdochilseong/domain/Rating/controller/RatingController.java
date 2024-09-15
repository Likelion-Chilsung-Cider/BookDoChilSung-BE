package com.likelion.Bookdochilseong.domain.Rating.controller;

import com.likelion.Bookdochilseong.domain.Rating.dto.request.RatingRequestDto;
import com.likelion.Bookdochilseong.domain.Rating.dto.response.RatingResponseDto;
import com.likelion.Bookdochilseong.domain.Rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponseDto> createRating(@RequestBody RatingRequestDto requestDto) {
        RatingResponseDto responseDto = ratingService.create(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> updateRating(@PathVariable Long id, @RequestBody RatingRequestDto requestDto) {
        RatingResponseDto responseDto = ratingService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDto> getRating(@PathVariable Long id) {
        RatingResponseDto responseDto = ratingService.get(id);
        return ResponseEntity.ok(responseDto);
    }
}

