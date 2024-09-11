package com.likelion.Bookdochilseong.domain.User.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDTO {
    //회원 번호
    @JsonProperty("id")
    public Long id;

    //자동 연결 설정을 비활성화한 경우만 존재.
    //true : 연결 상태, false : 연결 대기 상태
    @JsonProperty("has_signed_up")
    public Boolean hasSignedUp;

    //서비스에 연결 완료된 시각. UTC
    @JsonProperty("connected_at")
    public Date connectedAt;

    //카카오싱크 간편가입을 통해 로그인한 시각. UTC
    @JsonProperty("synched_at")
    public Date synchedAt;

    //사용자 프로퍼티
    @JsonProperty("properties")
    public HashMap<String, String> properties;

    //카카오 계정 정보
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    //uuid 등 추가 정보
    @JsonProperty("for_partner")
    public Partner partner;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
//        //닉네임 제공 동의 여부
//        @JsonProperty("profile_nickname_needs_agreement")
//        public Boolean isNickNameAgree;

        //프로필 사진 제공 동의 여부
        @JsonProperty("profile_image_needs_agreement")
        public Boolean isProfileImageAgree;

        //사용자 프로필 정보
        @JsonProperty("profile")
        public Profile profile;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            //닉네임
            @JsonProperty("nickname")
            public String nickName;

//            //닉네임이 기본 닉네임인지 여부
//            //true : 기본 닉네임, false : 사용자 등록
//            @JsonProperty("is_default_nickname")
//            public Boolean isDefaultNickName;

        }
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Partner {
        //고유 ID
        @JsonProperty("uuid")
        public String uuid;
    }
}
