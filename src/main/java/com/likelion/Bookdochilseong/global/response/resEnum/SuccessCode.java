package com.likelion.Bookdochilseong.global.response.resEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    // COMMON
    BOOK_LIST_FOUND(201, "책 리스트 조회가 완료되었습니다."),
    USER_BOOK_LIST_FOUND(201, "사용자별 책 리스트 조회가 완료되었습니다."),
    BOOK_INFO_ADDED(201,"책 정보 추가가 완료되었습니다.");
    ;

    private final Integer code;
    private final String message;
}
