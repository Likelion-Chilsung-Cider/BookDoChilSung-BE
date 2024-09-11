package com.likelion.Bookdochilseong.config.oauth;

import com.likelion.Bookdochilseong.domain.User.dto.AddUserResponseDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("getaccessToken: "+accessToken);
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        System.out.println(user);
        //saveOrUpdate(user);
        return user;
    }

    //유저가 있으면 업데이트, 없으면 유저 생성
    private TblUser saveOrUpdate(OAuth2User oAuth2User){
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String nickname = (String) attributes.get("nickname");
        TblUser user = userRepository.findByNickname(nickname)
                .orElse(TblUser.builder()
                        .nickname(nickname)
                        .build());
        return userRepository.save(user);
    }
}
