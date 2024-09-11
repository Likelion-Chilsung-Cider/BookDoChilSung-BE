package com.likelion.Bookdochilseong.domain.Book;

import com.likelion.Bookdochilseong.domain.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    // 책 정보 조회
    @GetMapping("/api/book/searchBook")
    public ResponseEntity<?> searchBook(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "startPage", defaultValue = "1") int startPage,
            @RequestParam(name = "maxResults", defaultValue = "10") int maxResults
    ) {
        return ResponseEntity.ok().body(bookService.searchBook(title, startPage, maxResults));
    }

    // 책 정보 조회 (사용자별)
    @GetMapping("/api/book/searchBookInfo")
    public ResponseEntity<?> searchBookInfo(
            @RequestParam(name = "user_seq") Long userSeq,
            @RequestParam(name = "reading_status") int readingStatus
    ) {
        return ResponseEntity.ok().body(bookService.searchBookInfo(userSeq, readingStatus));
    }
}
