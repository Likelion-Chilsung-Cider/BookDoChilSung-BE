package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
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
    public TblReadingStatus(LocalDate startDate, LocalDate endDate, int readingStatus) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.readingStatus = readingStatus;
    }
}
