package com.likelion.Bookdochilseong.domain.Review.repository;

import com.likelion.Bookdochilseong.entity.TblReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<TblReview , Long> {
    // 특정 ReadingStatus에 해당하는 리뷰 조회
    List<TblReview> findByTblReadingStatusId(Long readingStatusId);
}
