package com.likelion.Bookdochilseong.domain.tblBook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class BookResponseDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchBookListDTO{
        @JsonProperty("item")
        private List<SearchBookItemDTO> items;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchBookItemDTO{
        private String title;            // 책 제목
        private String description;      // 책 소개
        private String publisher;        // 출판사
        private String author;           // 저자
        @JsonProperty("cover")
        private String book_cover;       // 책 표지
        private String isbn;             // isbn
        @JsonProperty("page")
        private int pages;               // 페이지 수
    }
}
