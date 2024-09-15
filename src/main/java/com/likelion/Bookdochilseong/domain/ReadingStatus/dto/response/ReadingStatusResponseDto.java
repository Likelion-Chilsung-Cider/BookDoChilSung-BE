package com.likelion.Bookdochilseong.domain.ReadingStatus.dto.response;

import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadingStatusResponseDto {
    private Long id;
    private Long bookId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int status;

    @Builder
    public ReadingStatusResponseDto(Long id, Long bookId, LocalDate startDate, LocalDate endDate, int status) {
        this.id = id;
        this.bookId = bookId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

}
