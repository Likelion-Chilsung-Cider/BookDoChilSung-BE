package com.likelion.Bookdochilseong.domain.ReadingStatus.service;

import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.ReadingStatusRequestDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.response.ReadingStatusResponseDto;

public interface ReadingStatusService {
    //crud
    ReadingStatusResponseDto create(ReadingStatusRequestDto readingStatusRequestDto);
    ReadingStatusResponseDto update(Long id , ReadingStatusRequestDto readingStatusRequestDto);
    void delete(Long id);
    ReadingStatusResponseDto get(Long id);


}
