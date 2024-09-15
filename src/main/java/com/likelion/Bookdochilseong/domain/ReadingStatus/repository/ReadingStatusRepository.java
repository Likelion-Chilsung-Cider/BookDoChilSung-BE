package com.likelion.Bookdochilseong.domain.ReadingStatus.repository;

import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingStatusRepository extends JpaRepository<TblReadingStatus, Long> {
    Optional<TblReadingStatus> findByTblBook_IdAndTblUser_Id(Long bookId, Long userId);

}
