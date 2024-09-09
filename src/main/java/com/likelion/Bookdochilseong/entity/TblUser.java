package com.likelion.Bookdochilseong.entity;

import com.likelion.Bookdochilseong.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TblUser extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uid")
    private Long uId;
    @Column(name = "nickname", nullable = false , unique = true)
    private String nickname;
    @Column(name = "profileImg")
    private String profileImg;
    @Column(name = "password")
    private String password;
    @Column(name = "accessToken")
    private String accessToken;

    @Builder
    public TblUser(String nickname, String password,Long uId){
        this.nickname = nickname;
        this.password = password;
        this.uId = uId;
        this.profileImg = null;
    }
    public TblUser update(String name){
        this.nickname = nickname;

        return this;
    }
    //닉네임 수정 메서드
    public void patchNickname(String nickname){
        this.nickname = nickname;
    }
    //프로필이미지 수정 메서드
    public void patchProfileImg(String imgUrl){
        this.profileImg = imgUrl;
    }
    // UserDetails 오버라이드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername(){
        return nickname;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        //패스워드가 만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않음
    }
    @Override
    public boolean isEnabled(){
        //계정이 사용가능한지 확인하는 로직
        return true; //true -> 사용 가능
    }
}
