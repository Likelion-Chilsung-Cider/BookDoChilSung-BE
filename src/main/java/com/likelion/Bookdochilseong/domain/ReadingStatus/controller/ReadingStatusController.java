package com.likelion.Bookdochilseong.domain.ReadingStatus.controller;

import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.ReadingStatusRequestDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.response.ReadingStatusResponseDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.service.ReadingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
public class ReadingStatusController {
    private final ReadingStatusService readingStatusService;

    @PostMapping("/api/readingStatus/status")
    public ResponseEntity<ReadingStatusResponseDto> createReadingStatus (ReadingStatusRequestDto readingStatusRequestDto) {
        ReadingStatusResponseDto readingStatusResponseDto = readingStatusService.create(readingStatusRequestDto);
        return ResponseEntity.ok().body(readingStatusResponseDto);
    }

    @PutMapping("/modStatus")
    public ResponseEntity<ReadingStatusResponseDto> updateReadingStatus (ReadingStatusRequestDto readingStatusRequestDto) {
        ReadingStatusResponseDto readingStatusResponseDto= readingStatusService.update(readingStatusRequestDto);
        return ResponseEntity.ok().body(readingStatusResponseDto);
    }

    @GetMapping("/status")
    public ResponseEntity<ReadingStatusResponseDto> getReadingStatus (Long id) {
        ReadingStatusResponseDto responseDto = readingStatusService.get(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/status")
    public ResponseEntity<ReadingStatusResponseDto> deleteReadingStatus (Long id) {
        readingStatusService.delete(id);
        return ResponseEntity.ok().build();
    }
}
