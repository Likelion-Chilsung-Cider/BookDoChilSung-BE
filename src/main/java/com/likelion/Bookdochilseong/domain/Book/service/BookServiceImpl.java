package com.likelion.Bookdochilseong.domain.Book.service;

import com.likelion.Bookdochilseong.domain.Book.BookRepository;
import com.likelion.Bookdochilseong.domain.Book.dto.BookRequestDTO;
import com.likelion.Bookdochilseong.domain.Book.dto.BookResponseDTO;
import com.likelion.Bookdochilseong.entity.TblBook;
import com.likelion.Bookdochilseong.global.response.ApiResponse;
import com.likelion.Bookdochilseong.global.response.resEnum.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Value("${BOOKSEARCH_API_KEY}")
    private String apiKey;

    @Value("${BOOKSEARCH_API_URL}")
    private String apiUrl;

    private final WebClient.Builder webClientBuilder;
    private final BookRepository bookRepository;

    /**
     * 책 정보 조회
     * @param title
     * @param startPage
     * @param maxResults
     * @return SearchBookListDTO
     */
    @Override
    public ApiResponse<?> searchBook(String title, int startPage, int maxResults) {
        // 1. 알라딘 API URL
        String url = apiUrl + "?ttbkey=" + apiKey
                + "&Query=" + title + "&QueryType=Title&MaxResults=" + maxResults + "&start=" + startPage
                + "&SearchTarget=Book&output=js&Version=20131101";

        // 2. API 호출 후 책 리스트 받아오기
        BookResponseDTO.SearchBookListDTO searchBookListDTO = webClientBuilder.build()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(BookResponseDTO.SearchBookListDTO.class)
        .block();
        log.info("알라딘 API 호출 완료");

        return ApiResponse.SUCCESS(SuccessCode.BOOK_LIST_FOUND, searchBookListDTO);
    }

    /**
     * 책 정보 조회 (사용자별)
     * @param userSeq
     * @param readingStatus
     * @return
     */
    @Override
    public ApiResponse<?> searchBookInfo(Long userSeq, int readingStatus) {
        // TODO 수정중
        // 1. 독서 상태, 책 테이블 조인해서 데이터 조회 (userSeq or session) + readingStatus 조회조건 추가
        //List<ReadingStatusResponseDTO.SearchUserBookList> statusLists = readingStatusRepository.findByUserSeq(userSeq);

        return ApiResponse.SUCCESS(SuccessCode.USER_BOOK_LIST_FOUND);
    }

    /**
     * 책 정보 추가
     * @param addBookInfo
     * @return ApiResponse<?>
     */
    @Transactional
    @Override
    public ApiResponse<?> addBookInfo(BookRequestDTO.AddBookInfo addBookInfo) {
        // 1. 동일한 ISBN이 등록되어있는지 확인
        Optional<TblBook> isbnInfo = Optional.ofNullable(bookRepository.findByIsbn(addBookInfo.getIsbn()));

        // 2. 없으면 신규 책 등록
        if (isbnInfo.isEmpty()) {
            TblBook bookInfo = TblBook.builder()
                    .title(addBookInfo.getTitle())
                    .description(addBookInfo.getDescription())
                    .publisher(addBookInfo.getPublisher())
                    .author(addBookInfo.getAuthor())
                    .isbn(addBookInfo.getIsbn())
                    .pages(addBookInfo.getPages())
                    .book_cover(addBookInfo.getBook_cover())
                    .build();
            bookRepository.save(bookInfo);
            log.info("신규 책 정보 등록");
        } else {
            log.info("이미 등록되어 있는 책");
        }
        // TODO 사용자 상태 등록하는 거로 바꾸기
        return ApiResponse.SUCCESS(SuccessCode.BOOK_INFO_ADDED);
    }
}
