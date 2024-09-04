package com.likelion.Bookdochilseong.global.response.resEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    // COMMON
    CREATE_USER(201, "회원가입이 완료되었습니다.")
    ;

    private final Integer code;
    private final String message;
}
