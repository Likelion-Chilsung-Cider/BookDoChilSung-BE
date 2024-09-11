package com.likelion.Bookdochilseong.domain.User.controller;

import com.likelion.Bookdochilseong.domain.User.dto.MypageResponseDTO;
import com.likelion.Bookdochilseong.domain.User.dto.NicknameRequestDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.domain.User.service.MypageService;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final MypageService mypageService;
    //마이페이지 조회
    @GetMapping("/api/mypage")
    public ResponseEntity<?> myPage(){
        TblUser user = mypageService.getUser();
        MypageResponseDTO mypageResponseDTO = mypageService.mypage(user);
        return ResponseEntity.ok().body(mypageResponseDTO);
        //return ResponseEntity.ok().body(authentication);
    }
    //닉네임 수정
    @PostMapping("/api/mypage/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody NicknameRequestDTO requestDTO){
        TblUser user = mypageService.getUser();
        String oldNickname = user.getNickname();
        String result = mypageService.updateNickname(user,requestDTO);
        log.info(oldNickname+"님이 "+result+"으로 닉네임 변경");
        return ResponseEntity.ok().body(result);
    }
//    //프로필이미지 수정
//    @PostMapping("/api/mypage/img")
//    public ResponseEntity<?> updateProfileImg(@AuthenticationPrincipal TblUser user, @RequestBody MultipartFile img) throws IOException {
//        String imgUrl = mypageService.updateProfileImg(user,img);
//        log.info(user.getNickname() + "님이 프로필 사진을 변경하셨습니다.");
//        return ResponseEntity.ok().body(imgUrl);
//    }

}