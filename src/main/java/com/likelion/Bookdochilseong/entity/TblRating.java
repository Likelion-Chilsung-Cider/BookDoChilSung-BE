package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class TblRating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private int rating;


    @OneToOne(mappedBy = "tblRating")
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
