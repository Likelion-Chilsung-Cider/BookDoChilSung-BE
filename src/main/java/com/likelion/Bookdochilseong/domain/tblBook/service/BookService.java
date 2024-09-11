package com.likelion.Bookdochilseong.domain.tblBook.service;

import com.likelion.Bookdochilseong.global.response.ApiResponse;

public interface BookService {
    ApiResponse<?> searchBook(String title, int startPage,int maxResults); // 책 정보 조회
}
