package com.likelion.Bookdochilseong.domain.User.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.Bookdochilseong.domain.User.dto.MypageResponseDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MypageService {
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    @Value("버킷이름")
    private String bucket;
    //마이페이지 조회
    public MypageResponseDTO mypage(TblUser user){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        Period period = Period.between(user.getCreateDate().toLocalDate(),currentDate);
        return new MypageResponseDTO(user.getNickname(), user.getProfileImg(),period.plusDays(1).toString());
    }
    //닉네임 수정
    public String updateNickname(TblUser user, String nickname){
        Optional<TblUser> findUser = userRepository.findByNickname(nickname);
        if (findUser.isEmpty()){
            user.patchNickname(nickname);
            return nickname;
        }
        else{
            return null;
        }
    }
    //프로필이미지 수정
    public String updateProfileImg(TblUser user, MultipartFile img) throws IOException{
        String imgUrl = upload(img);
        user.patchProfileImg(imgUrl);
        return imgUrl;
    }

    //프로필이미지 s3에 저장
    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        //UUID 추가
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s","_");

        //String fileName = dirName + "/" + uniqueFileName;
        log.info("fileName: " + uniqueFileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, uniqueFileName,multipartFile.getInputStream(), metadata);
        String imgUrl = amazonS3.getUrl(bucket, uniqueFileName).toString();
        return imgUrl;
    }


}
