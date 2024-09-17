package com.likelion.Bookdochilseong.domain.ReadingStatus.service;

import com.likelion.Bookdochilseong.domain.Book.BookRepository;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.ReadingStatusRequestDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.request.UpdateReadingStatusRequestDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.dto.response.ReadingStatusResponseDto;
import com.likelion.Bookdochilseong.domain.ReadingStatus.repository.ReadingStatusRepository;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.domain.User.service.MypageService;
import com.likelion.Bookdochilseong.entity.TblBook;
import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadingStatusServiceImpl implements ReadingStatusService {

    private final ReadingStatusRepository readingStatusRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MypageService mypageService;

    /**
     * 책 정보 추가
     * @param addBookInfo
     */
    @Transactional
    public Long addBookInfo(ReadingStatusRequestDto addBookInfo) {
        log.info("책 정보 추가");
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
            TblBook newBookInfo = bookRepository.save(bookInfo);
            log.info("신규 책 정보 등록");
            return newBookInfo.getId();
        } else {
            log.info("이미 등록되어 있는 책");
            return isbnInfo.get().getId();
        }
    }

    @Override
    @Transactional
    public ReadingStatusResponseDto create(ReadingStatusRequestDto requestDto) {
        // 책 등록
        Long bookId = addBookInfo(requestDto);
        log.info("Book Id : ", bookId);

        TblUser tblUser = mypageService.getUser();
        TblBook tblBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        TblReadingStatus readingStatus = TblReadingStatus.builder()
                .tblUser(tblUser)
                .tblBook(tblBook)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .tblReadingStatus(requestDto.getStatus())
                .build();


        TblReadingStatus saved = readingStatusRepository.save(readingStatus);
        return ReadingStatusResponseDto.builder()
                .id(saved.getId())
                .bookId(saved.getTblBook().getId())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .status(saved.getTblReadingStatus())
                .build();
    }

    @Override
    @Transactional
    public ReadingStatusResponseDto update(Long id , UpdateReadingStatusRequestDto requestDto) {
        // 사용자의 독서 상태를 업데이트합니다.
        TblUser tblUser = mypageService.getUser();
        TblReadingStatus existingStatus = readingStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("독서 상태를 찾을 수 없습니다."));

        // 요청된 데이터로 독서 상태 업데이트
        existingStatus.updateStatus(requestDto.getStatus(), requestDto.getEndDate());

        TblReadingStatus updatedStatus = readingStatusRepository.save(existingStatus);

        return ReadingStatusResponseDto.builder()
                .id(updatedStatus.getId())
                .bookId(updatedStatus.getTblBook().getId())
                .startDate(updatedStatus.getStartDate())
                .endDate(updatedStatus.getEndDate())
                .status(updatedStatus.getTblReadingStatus())
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TblUser tblUser = mypageService.getUser();
        TblReadingStatus readingStatus = readingStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("독서 상태를 찾을 수 없습니다."));

        // 사용자가 맞는지 확인
        if (!readingStatus.getTblUser().getId().equals(tblUser.getId())) {
            throw new SecurityException("해당 사용자가 아님");
        }

        readingStatusRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingStatusResponseDto get(Long id) {
        TblUser tblUser = mypageService.getUser();
        TblReadingStatus readingStatus = readingStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("독서 상태를 찾을 수 없습니다."));

        return ReadingStatusResponseDto.builder()
                .id(readingStatus.getId())
                .bookId(readingStatus.getTblBook().getId())
                .startDate(readingStatus.getStartDate())
                .endDate(readingStatus.getEndDate())
                .status(readingStatus.getTblReadingStatus())
                .build();
    }
}
