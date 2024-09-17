package com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateReadingStatusRequestDto {
    private LocalDate endDate;
    private int status;
}
