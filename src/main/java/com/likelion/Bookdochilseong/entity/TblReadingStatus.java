package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TblReadingStatus extends BaseEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(name = "tblReadingStatus")
    private int tblReadingStatus;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private TblBook tblBook;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TblUser tblUser;

    @OneToMany(mappedBy = "tblReadingStatus", cascade = CascadeType.ALL)
    private List<TblReview> reviews;

    @OneToOne(mappedBy = "tblReadingStatus", cascade = CascadeType.ALL)
    private TblRating tblRating;

    @Builder
    public TblReadingStatus(TblUser tblUser, TblBook tblBook, LocalDate startDate, LocalDate endDate, int tblReadingStatus) {
        this.tblUser = tblUser;
        this.tblBook = tblBook;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tblReadingStatus = tblReadingStatus;
    }


    public void updateStatus(int readingStatus, LocalDate endDate) {
        this.tblReadingStatus = readingStatus;
        this.endDate = endDate;
    }

}
