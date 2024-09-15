package com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReadingStatusRequestDto {
    private Long bookId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int status;
}
