package com.likelion.Bookdochilseong.domain.Rating.service;

import com.likelion.Bookdochilseong.domain.Book.BookRepository;
import com.likelion.Bookdochilseong.domain.Rating.dto.request.RatingRequestDto;
import com.likelion.Bookdochilseong.domain.Rating.dto.response.RatingResponseDto;
import com.likelion.Bookdochilseong.domain.Rating.repository.RatingRepository;
import com.likelion.Bookdochilseong.domain.ReadingStatus.repository.ReadingStatusRepository;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.domain.User.service.MypageService;
import com.likelion.Bookdochilseong.entity.TblRating;
import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ReadingStatusRepository readingStatusRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MypageService mypageService;

    @Override
    @Transactional
    public RatingResponseDto create(RatingRequestDto requestDto) {
        TblUser tblUser = mypageService.getUser();
        // User와 Book ID로 ReadingStatus 찾기
        TblReadingStatus readingStatus = readingStatusRepository.findByTblBook_IdAndTblUser_Id(
                        requestDto.getBookId(), tblUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 책의 독서 상태를 찾을 수 없습니다."));

        // 평점 저장
        TblRating rating = TblRating.builder()
                .tblReadingStatus(readingStatus)
                .rating(requestDto.getRating())
                .build();

        TblRating saved = ratingRepository.save(rating);

        return RatingResponseDto.builder()
                .id(saved.getId())
                .readingStatusId(saved.getTblReadingStatus().getId())
                .rating(requestDto.getRating())
                .build();
    }

    @Override
    @Transactional
    public RatingResponseDto update(Long id, RatingRequestDto requestDto) {
        TblRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 평점을 찾을 수 없습니다."));

        rating.updateRating(requestDto.getRating());

        return RatingResponseDto.builder()
                .id(rating.getId())
                .readingStatusId(rating.getTblReadingStatus().getId())
                .rating(rating.getRating())
                .build();
    }

    @Override
    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public RatingResponseDto get(Long id) {
        TblRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 평점을 찾을 수 없습니다."));

        return RatingResponseDto.builder()
                .id(rating.getId())
                .readingStatusId(rating.getTblReadingStatus().getId())
                .rating(rating.getRating())
                .build();
    }
}
