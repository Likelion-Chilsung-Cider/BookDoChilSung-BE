package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class TblReadingStatus extends BaseEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private int readingStatus;

    @ManyToOne
    @JoinColumn(name = "book_id" , nullable = false)
    private TblBook tblBook;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private TblUser tblUser;

    @Builder
    public TblReadingStatus(TblUser tblUser, TblBook tblBook, LocalDate startDate, LocalDate endDate, int readingStatus) {
        this.tblUser = tblUser;
        this.tblBook = tblBook;
        this.startDate = startDate;
        this.endDate = endDate;
        this.readingStatus = readingStatus;
    }


    public void updateStatus(int readingStatus, LocalDate endDate) {
        this.readingStatus = readingStatus;
        this.endDate = endDate;
    }

}
