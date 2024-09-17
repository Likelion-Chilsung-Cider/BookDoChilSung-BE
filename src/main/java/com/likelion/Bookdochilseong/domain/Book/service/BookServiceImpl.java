package com.likelion.Bookdochilseong.domain.Book.service;

import com.likelion.Bookdochilseong.domain.Book.BookRepository;
import com.likelion.Bookdochilseong.domain.Book.dto.BookResponseDTO;
import com.likelion.Bookdochilseong.domain.Rating.repository.RatingRepository;
import com.likelion.Bookdochilseong.entity.TblRating;
import com.likelion.Bookdochilseong.global.response.ApiResponse;
import com.likelion.Bookdochilseong.global.response.resEnum.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

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
    private final RatingRepository ratingRepository;

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
     * @return bookList
     */
    @Override
    public ApiResponse<?> searchBookInfo(Long userSeq, int readingStatus) {
        log.info("사용자별 책 정보 조회 시작 userSeq : ", userSeq);

        // 1. 0이면 전체 조회(0,1,2)
        List<Integer> statuses;

        if (readingStatus == 0) statuses = Arrays.asList(0, 1, 2);
        else  statuses = Collections.singletonList(readingStatus);

        // 2. 사용자ID, 독서 상태 값으로 사용자별 책 리스트 조회
        List<Object[]> statusLists = bookRepository.findByTblUser_IdAndStatus(userSeq, statuses);
        List<BookResponseDTO.SearchUserBookListDTO> bookList = new ArrayList<>();

        // 3. 조회해 온 값으로 책 정보 데이터 세팅
        for (Object[] objects : statusLists) {
            Long readingStatusId = ((Number) objects[14]).longValue();
            log.info("readingStatusId : " + readingStatusId);

            // rating 값 조회
            int rating;
            TblRating ratingInfo = ratingRepository.findByTblReadingStatus_Id(readingStatusId);

            // 조회한 rating 값이 없으면 0으로
            if (ratingInfo != null) rating = ratingInfo.getRating();
            else rating = 0;

            // 데이터 세팅
            BookResponseDTO.SearchUserBookListDTO book = BookResponseDTO.SearchUserBookListDTO.builder()
                    .book_seq(((Number) objects[0]).longValue())
                    //.createDate(((Timestamp) objects[1]).toLocalDateTime().toLocalDate().atStartOfDay())
                    //.updateDate(((Timestamp) objects[2]).toLocalDateTime().toLocalDate().atStartOfDay())
                    .author((String) objects[3])
                    .book_cover((String) objects[4])
                    .description((String) objects[5])
                    .isbn((String) objects[6])
                    .pages(((Number) objects[7]).intValue())
                    .publisher((String) objects[8])
                    .title((String) objects[9])
                    .user_id((Long) objects[10])
                    .reading_status((int) objects[11])
                    .rating(rating)
                    .start_date((java.sql.Date) objects[12])
                    .end_date((java.sql.Date) objects[13])
                    .build();
            bookList.add(book);
        }
        return ApiResponse.SUCCESS(SuccessCode.USER_BOOK_LIST_FOUND, bookList);
    }
}
