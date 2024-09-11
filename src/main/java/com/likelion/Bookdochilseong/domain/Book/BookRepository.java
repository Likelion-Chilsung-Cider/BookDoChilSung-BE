package com.likelion.Bookdochilseong.domain.Book;

import com.likelion.Bookdochilseong.entity.TblBook;
import com.likelion.Bookdochilseong.entity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<TblBook, Long> {
    TblBook findByIsbn(String isbn);
}
