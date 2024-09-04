package com.likelion.Bookdochilseong.global.response.resEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 4xx
    PASSWORD_INCORRECT(401, HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    // 5xx
    CREATE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "객체 생성에 실패했습니다.")
    ;

    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
