package com.likelion.Bookdochilseong.domain.tblBook;

import com.likelion.Bookdochilseong.domain.tblBook.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    // 책 조회
    @GetMapping("/api/book/searchBook")
    public ResponseEntity<?> searchBook(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "startPage", defaultValue = "1") int startPage,
            @RequestParam(name = "maxResults", defaultValue = "10") int maxResults
    ) {
        return ResponseEntity.ok().body(bookService.searchBook(title, startPage, maxResults));
    }
}
