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
    private String title;          // 책 제목
    private String description;    // 책 소개
    private String publisher;      // 출판사 이름
    private String author;         // 저자 이름
    private String isbn;           // ISBN 번호
    private int pages;             // 페이지 수
    private String book_cover;     // 책 표지
}
