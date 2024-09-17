package com.likelion.Bookdochilseong.domain.ReadingStatus.controller;

import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.ReadingStatusRequestDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.UpdateReadingStatusRequestDto;
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
    public ResponseEntity<ReadingStatusResponseDto> createReadingStatus (@RequestBody ReadingStatusRequestDto readingStatusRequestDto) {
        ReadingStatusResponseDto readingStatusResponseDto = readingStatusService.create(readingStatusRequestDto);
        return ResponseEntity.ok().body(readingStatusResponseDto);
    }

    @PutMapping("/api/readingStatus/status/{id}")
    public ResponseEntity<ReadingStatusResponseDto> updateReadingStatus
            (@PathVariable Long id,
             @RequestBody UpdateReadingStatusRequestDto requestDto) {
        ReadingStatusResponseDto readingStatusResponseDto= readingStatusService.update(id, requestDto);
        return ResponseEntity.ok().body(readingStatusResponseDto);
    }

    @GetMapping("/api/readingStatus/status/{id}")
    public ResponseEntity<ReadingStatusResponseDto> getReadingStatus (@PathVariable Long id) {
        ReadingStatusResponseDto responseDto = readingStatusService.get(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/api/readingStatus/status/{id}")
    public ResponseEntity<ReadingStatusResponseDto> deleteReadingStatus (@PathVariable Long id) {
        readingStatusService.delete(id);
        return ResponseEntity.ok().build();
    }
}
