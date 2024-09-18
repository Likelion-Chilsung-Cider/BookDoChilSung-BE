package com.likelion.Bookdochilseong.domain.User.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImgRequestDTO {
    private MultipartFile img;
}
