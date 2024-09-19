package com.likelion.Bookdochilseong.config.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.Bookdochilseong.config.jwt.TokenProvider;
import com.likelion.Bookdochilseong.domain.User.RefreshToken;
import com.likelion.Bookdochilseong.domain.User.repository.RefreshTokenRepository;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.domain.User.service.UserService;
import com.likelion.Bookdochilseong.entity.TblUser;
import com.likelion.Bookdochilseong.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/" ;

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    //private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserRepository userRepository;
    private OAuth2UserInfo oAuth2UserInfo = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        log.info("로그인 요청");
        oAuth2UserInfo = new KakaoUserInfo(token.getPrincipal().getAttributes());
        Long providerId = Long.parseLong(oAuth2UserInfo.getUid());
        String name = oAuth2UserInfo.getName();
        log.info("유저 이름 : "+ name);
        log.info("유저 아이디 : " + Long.toString(providerId));
        TblUser existUser = userRepository.findByuId(providerId);
        TblUser user;

        if (existUser == null){
            log.info("신규유저 입니다.");
            user = new TblUser(name, providerId);
            userRepository.save(user);
            //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
            String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
            saveRefreshToken(user.getId(), refreshToken);
            addRefreshTokenToCookie(request, response, refreshToken);
            //액세스토큰 생성 -> 패스에 액세스 토큰 추가
            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
            log.info("ACCESS_TOKEN : " + accessToken);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(tokens);
            response.getWriter().write(responseBody);
            response.getWriter().flush();
            //String targetUrl = getTargetUrl(accessToken);
            //인증 관련 설정값, 쿠키 제거
            //clearAuthenticationAttributes(request, response);
            //리다이렉트
            //getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else{
            log.info("기존 유저");
            user = existUser;
            //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
            String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
            saveRefreshToken(user.getId(), refreshToken);
            addRefreshTokenToCookie(request, response, refreshToken);
            //액세스토큰 생성 -> 패스에 액세스 토큰 추가
            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
            log.info("ACCESS_TOKEN : " + accessToken);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(tokens);
            response.getWriter().write(responseBody);
            response.getWriter().flush();
            //String targetUrl = getTargetUrl(accessToken);
            //인증 관련 설정값, 쿠키 제거
            //clearAuthenticationAttributes(request, response);
            //리다이렉트
            //getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        TblUser user = userService.findById((Long)oAuth2User.getAttributes().get("id")); //주의
//        //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
//        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
//        saveRefreshToken(user.getId(), refreshToken);
//        addRefreshTokenToCookie(request, response, refreshToken);
//        //액세스토큰 생성 -> 패스에 액세스 토큰 추가
//        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
//        String targetUrl = getTargetUrl(accessToken);
//        //인증 관련 설정값, 쿠키 제거
//        clearAuthenticationAttributes(request, response);
//        //리다이렉트
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }

    //생성된 리프레시 토큰을 전달받아 디비에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    //생성된 리프레시토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshToken, cookieMaxAge);
    }

//    //인증 관련 설정값, 쿠키 제거
//    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
//        super.clearAuthenticationAttributes(request);
//        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }

    //액세스 토큰을 패스에 추가
    private String getTargetUrl(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                //.queryParam("token", token)
                .build()
                .toUriString();
    }
}
