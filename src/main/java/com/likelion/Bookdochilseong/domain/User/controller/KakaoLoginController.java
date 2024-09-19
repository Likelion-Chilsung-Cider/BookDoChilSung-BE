//package com.likelion.Bookdochilseong.domain.User.controller;
//
//import com.likelion.Bookdochilseong.config.TokenAuthenticationFilter;
//import com.likelion.Bookdochilseong.domain.User.dto.AddUserResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.dto.KakaoUserInfoResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.dto.MypageResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
//import com.likelion.Bookdochilseong.domain.User.service.KakaoService;
//import com.likelion.Bookdochilseong.domain.User.service.MypageService;
//import com.likelion.Bookdochilseong.entity.TblUser;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class KakaoLoginController {
//    private final KakaoService kakaoService;
//    //카카오에서 인가코드 받아오기
//    @GetMapping("/main")
//    public ResponseEntity<?> getCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response)throws IOException {
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//        //HashMap<String, Object> userInfo = kakaoService.getKakaoUserInfo(accessToken);
//        //AddUserResponseDTO result = kakaoService.KakaoUserLogin(userInfo,request,response);
//        //return new ResponseEntity<>(HttpStatus.OK);
//        KakaoUserInfoResponseDTO userInfo = kakaoService.getKakaoUserInfo(accessToken);
//        AddUserResponseDTO loginResult = kakaoService.KakaoUserLogin(userInfo,request,response);
//        Map<String,Object> getUserInfo = kakaoService.getUserInfo(accessToken);
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        OAuth2User oAuth2User = new DefaultOAuth2User(authorities,getUserInfo,"id");
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(oAuth2User,null,oAuth2User.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + loginResult.getAccessToken());
//        return ResponseEntity.ok().headers(headers).body(loginResult);
//    }
//}
