package com.likelion.Bookdochilseong.domain.User.service;

import com.likelion.Bookdochilseong.domain.User.dto.AddUserRequestDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    public Long save(AddUserRequestDTO addUserRequestDTO){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        return userRepository.save(TblUser.builder()
//                .nickname(addUserRequestDTO.getNickname())
//                //.password(addUserRequestDTO.getPassword())
//                .build()).getId();
//    }

    public TblUser findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Unexpected User"));
    }

    public TblUser findByNickname(String nickname){
        return userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("Unexpected User"));
    }
}
