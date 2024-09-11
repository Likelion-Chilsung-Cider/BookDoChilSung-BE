package com.likelion.Bookdochilseong.domain.User.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddUserResponseDTO {
    private Long uId;
    private String nickname;
    private String accessToken;
    private String role;

    public AddUserResponseDTO(Long uId, String nickname, String accessToken){
        this.uId = uId;
        this.nickname = nickname;
        this.accessToken = accessToken;
    }
}
