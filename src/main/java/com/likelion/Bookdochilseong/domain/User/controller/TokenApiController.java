package com.likelion.Bookdochilseong.domain.User.controller;

import com.likelion.Bookdochilseong.domain.User.dto.CreateAccessTokenRequestDTO;
import com.likelion.Bookdochilseong.domain.User.dto.CreateAccessTokenResponseDTO;
import com.likelion.Bookdochilseong.domain.User.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<?> createNewAccessToken(@RequestBody CreateAccessTokenRequestDTO requestDTO){
        String newAccessToken = tokenService.createNewAccessToken(requestDTO.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponseDTO(newAccessToken));
    }
}
