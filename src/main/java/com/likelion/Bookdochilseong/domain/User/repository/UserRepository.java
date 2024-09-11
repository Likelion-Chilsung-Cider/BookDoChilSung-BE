package com.likelion.Bookdochilseong.domain.User.repository;

import com.likelion.Bookdochilseong.entity.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TblUser, Long> {
    Optional<TblUser> findByNickname(String nickname);
    Optional<TblUser> findByuId(Long uId);
}
