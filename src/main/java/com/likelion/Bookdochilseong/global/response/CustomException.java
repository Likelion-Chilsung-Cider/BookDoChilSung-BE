package com.likelion.Bookdochilseong.global.response;

import com.likelion.Bookdochilseong.global.response.resEnum.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode code;
}
