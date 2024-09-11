package com.likelion.Bookdochilseong.domain.User.service;

import com.likelion.Bookdochilseong.domain.User.RefreshToken;
import com.likelion.Bookdochilseong.domain.User.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
