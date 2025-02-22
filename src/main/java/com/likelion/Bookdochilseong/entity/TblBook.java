package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TblBook extends BaseEntity {
    @Id
    @Comment(value="구분자")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String publisher;
    @Column
    private String author;
    @Column
    private String book_cover;
    @Column
    private String isbn;
    @Column
    private int pages;

    @OneToMany(mappedBy = "tblBook", cascade = CascadeType.REMOVE)
    private List<TblReadingStatus> readingStatuses;
}