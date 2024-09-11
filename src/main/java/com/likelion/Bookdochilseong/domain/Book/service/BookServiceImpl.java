package com.likelion.Bookdochilseong.domain.Book.service;

import com.likelion.Bookdochilseong.domain.Book.dto.BookResponseDTO;
import com.likelion.Bookdochilseong.global.response.ApiResponse;
import com.likelion.Bookdochilseong.global.response.resEnum.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Value("${BOOKSEARCH_API_KEY}")
    private String apiKey;

    @Value("${BOOKSEARCH_API_URL}")
    private String apiUrl;

    private final WebClient.Builder webClientBuilder;

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
        // 1. 독서 상태, 책 테이블 조인해서 데이터 조회 (userSeq or session) + readingStatus 조회조건 추가
        //List<ReadingStatusResponseDTO.SearchUserBookList> statusLists = readingStatusRepository.findByUserSeq(userSeq);

        // TODO 성공 코드 수정
        return ApiResponse.SUCCESS(SuccessCode.USER_BOOK_LIST_FOUND);
    }
}
