package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TblReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tblReadingStatus_id", nullable = false)
    private TblReadingStatus tblReadingStatus;

    @Column(name = "comment",nullable = false, length = 500)
    private String comment;

    @Builder
    public TblReview(TblReadingStatus tblReadingStatus, String comment) {
        this.tblReadingStatus = tblReadingStatus;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
