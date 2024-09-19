//package com.likelion.Bookdochilseong.domain.User.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.likelion.Bookdochilseong.config.jwt.TokenProvider;
//import com.likelion.Bookdochilseong.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
//import com.likelion.Bookdochilseong.config.oauth.OAuth2SuccessHandler;
//import com.likelion.Bookdochilseong.domain.User.RefreshToken;
//import com.likelion.Bookdochilseong.domain.User.dto.AddUserRequestDTO;
//import com.likelion.Bookdochilseong.domain.User.dto.AddUserResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.dto.KakaoResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.dto.KakaoUserInfoResponseDTO;
//import com.likelion.Bookdochilseong.domain.User.repository.RefreshTokenRepository;
//import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
//import com.likelion.Bookdochilseong.entity.TblUser;
//import com.likelion.Bookdochilseong.util.CookieUtil;
//import com.sun.jdi.ObjectCollectedException;
//import io.netty.handler.codec.http.HttpHeaderValues;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.likelion.Bookdochilseong.config.oauth.OAuth2SuccessHandler.REFRESH_TOKEN_DURATION;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//@Transactional
//public class KakaoService {
//    //private String clientId;
//    //private String KAUTH_TOKEN_URL_HOST;
//    //private String KAUTH_USER_URL_HOST;
//    @Value("http://bookdochilsung-env.ap-northeast-2.elasticbeanstalk.com/logun/oauth2/code/kakao")
//    private String redirectUri;
//
//    private final UserRepository userRepository;
//    private OAuth2SuccessHandler oAuth2SuccessHandler;
//    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
//    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
//    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
//    public static final String REDIRECT_PATH = "http://bookdochilsung-env.ap-northeast-2.elasticbeanstalk.com/logun/oauth2/code/kakao" ;
//    public static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
//    public static final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
//    public static final String clientId = "d6acae5182521fee4e7186969165e210";
//    private final TokenProvider tokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
//    private UserService userService;
//
////    @Autowired
////    public KakaoService(@Value("d6acae5182521fee4e7186969165e210") String clientId){
////        this.clientId = clientId;
////        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
////        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
////    }
//
//    //카카오에서 코드로 토큰 발급받기
//    public String getAccessTokenFromKakao(String code) {
//
//        KakaoResponseDTO kakaoResponseDTO = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/oauth/token")
//                        .queryParam("grant_type", "authorization_code")
//                        .queryParam("client_id", clientId)
//                        .queryParam("code", code)
//                        .build(true))
//                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
//                .retrieve()
//                //TODO : Custom Exception
////                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
////                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
//                .bodyToMono(KakaoResponseDTO.class)
//                .block();
//
//
//        log.info(" [Kakao Service] Access Token ------> {}", kakaoResponseDTO.getAccessToken());
//        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoResponseDTO.getRefreshToken());
//        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
////        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());
////        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());
//
//        return kakaoResponseDTO.getAccessToken();
//    }
//    //토큰에서 유저 정보 가져오기
//    public /*HashMap<String, Object>*/KakaoUserInfoResponseDTO getKakaoUserInfo(String accessToken){
////        HashMap<String, Object> userInfo = new HashMap<String, Object>();
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Authorization", "Bearer" + accessToken);
////        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
////
////        //Http 요청 보내기
////        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
////        RestTemplate rt = new RestTemplate();
////        ResponseEntity<String> response = rt.exchange(
////                "https://kapi.kakao.com/v2/user/me",
////                HttpMethod.POST,
////                kakaoUserInfoRequest,
////                String.class
////        );
////
////        //responsebody에 있는 정보 꺼내기
////        String responseBody = response.getBody();
////        ObjectMapper objectMapper = new ObjectMapper();
////        JsonNode jsonNode = null;
////        try{
////            jsonNode = objectMapper.readTree(responseBody);
////        } catch (JsonProcessingException e){
////            e.printStackTrace();
////        }
////
////        Long id = jsonNode.get("id").asLong();
////        String nickname = jsonNode.get("properties").get("nickname").asText();
////
////        userInfo.put("id", id);
////        userInfo.put("nickname", nickname);
////
////        return userInfo;
//        KakaoUserInfoResponseDTO userInfo = WebClient.create(KAUTH_USER_URL_HOST)
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/v2/user/me")
//                        .build(true))
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
//                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
//                .retrieve()
//                //TODO : Custom Exception
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
//                .bodyToMono(KakaoUserInfoResponseDTO.class)
//                .block();
//
//        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
//        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
//
//        return userInfo;
//    }
//
//    public Map<String,Object> getUserInfo(String accessToken){
//        Map<String,Object> userInfo = WebClient.create(KAUTH_USER_URL_HOST)
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/v2/user/me")
//                        .build(true))
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
//                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
//                .retrieve()
//                //TODO : Custom Exception
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
//                .bodyToMono(new ParameterizedTypeReference<Map<String,Object>>() {
//                })
//                .block();
//
//        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.get("id"));
//        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.get("properties"));
//        return userInfo;
//    }
//
//    public AddUserResponseDTO KakaoUserLogin(KakaoUserInfoResponseDTO userInfo,HttpServletRequest request, HttpServletResponse response){
//        Long uid = userInfo.getId();
//        String nickname = userInfo.getKakaoAccount().getProfile().getNickName();
//
//        TblUser user = userRepository.findByNickname(nickname).orElse(null);
//        if (userRepository.findByNickname(nickname).isEmpty()){
//            TblUser tblUser = TblUser.builder().nickname(nickname).uId(uid).build();
//            userRepository.save(tblUser);
//            return new AddUserResponseDTO(uid,nickname,generateToken(request,response,tblUser));
//        }
//        else{
//            return new AddUserResponseDTO(uid,nickname,generateToken(request,response,user));
//        }
//    }
//
//    public String generateToken(HttpServletRequest request,HttpServletResponse response, TblUser user){
//        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
//        saveRefreshToken(user.getId(), refreshToken);
//        addRefreshTokenToCookie(request, response, refreshToken);
//        String accessToken = tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
//        String targetUrl = getTargetUrl(accessToken);
//        return accessToken;
//    }
//
//    //생성된 리프레시 토큰을 전달받아 디비에 저장
//    private void saveRefreshToken(Long userId, String newRefreshToken){
//        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
//                .map(entity -> entity.update(newRefreshToken))
//                .orElse(new RefreshToken(userId, newRefreshToken));
//
//        refreshTokenRepository.save(refreshToken);
//    }
//    //생성된 리프레시토큰을 쿠키에 저장
//    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN_COOKIE_NAME);
//        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshToken, cookieMaxAge);
//    }
//    private String getTargetUrl(String token){
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    }
//
//}
