package com.likelion.Bookdochilseong.domain.User.service;

import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
