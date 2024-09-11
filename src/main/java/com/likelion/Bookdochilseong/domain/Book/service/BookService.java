package com.likelion.Bookdochilseong.domain.Book.service;

import com.likelion.Bookdochilseong.global.response.ApiResponse;

public interface BookService {
    ApiResponse<?> searchBook(String title, int startPage,int maxResults); // 책 정보 조회
    ApiResponse<?> searchBookInfo(Long userSeq, int readingStatus); // 책 정보 조회 (사용자별)
}
