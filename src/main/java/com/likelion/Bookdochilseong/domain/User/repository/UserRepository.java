package com.likelion.Bookdochilseong.domain.User.repository;

import com.likelion.Bookdochilseong.entity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TblUser, Long> {
    Optional<TblUser> findByNickname(String nickname);
}
