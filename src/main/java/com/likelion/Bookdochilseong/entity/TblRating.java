package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class TblRating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;


    @ManyToOne
    @JoinColumn(name = "reading_status_id", nullable = false)
    private TblReadingStatus tblReadingStatus;

    @Builder
    public TblRating(TblReadingStatus tblReadingStatus, int rating) {
        this.tblReadingStatus = tblReadingStatus;
        this.rating = rating;
    }

    public void updateRating(int rating) {
        this.rating = rating;
    }


}
