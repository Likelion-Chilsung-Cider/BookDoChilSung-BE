package com.likelion.Bookdochilseong.domain.Book;

import com.likelion.Bookdochilseong.entity.TblBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<TblBook, Long> {
    TblBook findByIsbn(String isbn);

    @Query(value = "SELECT DISTINCT b.id, b.create_date, b.update_date, b.author, b.book_cover, b.description, b.isbn, b.pages, b.publisher, b.title, " +
            "r.user_id, r.tbl_reading_status, r.start_date, r.end_date, r.id " +
            "FROM tbl_book b " +
            "LEFT JOIN tbl_reading_status r ON b.id = r.book_id " +
            "WHERE r.user_id = :userId " +
            "AND r.tbl_reading_status IN :status",
            nativeQuery = true)
    List<Object[]> findByTblUser_IdAndStatus(@Param("userId") Long userId, @Param("status") List<Integer> status);
}
