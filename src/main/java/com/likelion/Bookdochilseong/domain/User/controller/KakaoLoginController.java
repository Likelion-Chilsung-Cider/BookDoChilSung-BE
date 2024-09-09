package com.likelion.Bookdochilseong.domain.User.controller;

import com.likelion.Bookdochilseong.domain.User.dto.AddUserResponseDTO;
import com.likelion.Bookdochilseong.domain.User.dto.KakaoUserInfoResponseDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.domain.User.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoService kakaoService;
    //카카오에서 인가코드 받아오기
    @GetMapping("/main")
    public ResponseEntity<?> getCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response)throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        //HashMap<String, Object> userInfo = kakaoService.getKakaoUserInfo(accessToken);
        //AddUserResponseDTO result = kakaoService.KakaoUserLogin(userInfo,request,response);
        //return new ResponseEntity<>(HttpStatus.OK);
        KakaoUserInfoResponseDTO userInfo = kakaoService.getKakaoUserInfo(accessToken);
        AddUserResponseDTO loginResult = kakaoService.KakaoUserLogin(userInfo,request,response);
        return ResponseEntity.ok().body(loginResult);
    }
}
