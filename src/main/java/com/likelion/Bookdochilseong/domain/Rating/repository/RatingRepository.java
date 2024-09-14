package com.likelion.Bookdochilseong.domain.Rating.repository;

import com.likelion.Bookdochilseong.entity.TblRating;
import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<TblRating, Long> {

}

