package com.likelion.Bookdochilseong.domain.User.dto;

import lombok.Data;

@Data
public class MypageResponseDTO {
    private String nickname;
    private String profileImgUrl;
    private String date;
    //월별 독서량???

    public MypageResponseDTO(String nickname, String profileImgUrl, String date){
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.date = date;
    }
}
