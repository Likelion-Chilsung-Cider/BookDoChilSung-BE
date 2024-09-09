package com.likelion.Bookdochilseong.domain.User.service;

import com.likelion.Bookdochilseong.domain.User.dto.MypageResponseDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MypageService {
    private final UserRepository userRepository;

    //마이페이지 조회
    public MypageResponseDTO mypage(Long userId){
        Optional<TblUser> findUser = userRepository.findById(userId);
        if(findUser.isPresent()){
            TblUser user = findUser.get();
            LocalDate currentDate = LocalDateTime.now().toLocalDate();
            Period period = Period.between(user.getCreateDate().toLocalDate(),currentDate);
            return new MypageResponseDTO(user.getNickname(), user.getProfileImg(),period.plusDays(1).toString());
        }
        else{
            return null;
        }
    }
    //닉네임 수정
    public String updateNickname(TblUser user, String nickname){
        Optional<TblUser> findUser = userRepository.findByNickname(nickname);
        if (findUser.isEmpty()){
            user.patchNickname(nickname);
            String result = nickname + "으로 수정완료";
            return result;
        }
        else{
            return null;
        }
    }
    //프로필이미지 수정
    public String
}
