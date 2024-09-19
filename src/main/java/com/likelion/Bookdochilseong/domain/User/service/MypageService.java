package com.likelion.Bookdochilseong.domain.User.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.Bookdochilseong.domain.ReadingStatus.repository.ReadingStatusRepository;
import com.likelion.Bookdochilseong.domain.User.dto.MypageResponseDTO;
import com.likelion.Bookdochilseong.domain.User.dto.NicknameRequestDTO;
import com.likelion.Bookdochilseong.domain.User.repository.UserRepository;
import com.likelion.Bookdochilseong.entity.TblReadingStatus;
import com.likelion.Bookdochilseong.entity.TblUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MypageService {
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    private final ReadingStatusRepository readingStatusRepository;
    @Value("bookdochilseong")
    private String bucket;
    //유저 가져오기
    public TblUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uId = userDetails.getUsername();
        TblUser user = userRepository.findByuId(Long.parseLong(uId));
        return user;
    }


    //마이페이지 조회
    public MypageResponseDTO mypage(TblUser user){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        Period period = Period.between(user.getCreateDate().toLocalDate(),currentDate);
        Integer date = period.getDays()+1;
        return new MypageResponseDTO(user.getNickname(), user.getProfileImg(),date);
    }
    //닉네임 수정
    @Transactional
    public String updateNickname(TblUser user, NicknameRequestDTO requestDTO){
        Optional<TblUser> findUser = userRepository.findByNickname(requestDTO.getNickname());
        if (findUser.isEmpty()){
            user.patchNickname(requestDTO.getNickname());
            return requestDTO.getNickname();
        }
        else{
            return null;
        }
    }
//    //프로필이미지 수정
    @Transactional
    public String updateProfileImg(TblUser user, MultipartFile img) throws IOException{
        String imgUrl = upload(img);
        user.patchProfileImg(imgUrl);
        return imgUrl;
    }

    //월별 독서량 통계
    public List<Integer> monthlyBook(TblUser user){
        List<Integer> record = new ArrayList<>();
        for(int i = 0 ;i < 5; i++){
            Integer currentMonth = LocalDate.now().getMonthValue() - i;
            Long countBook = readingStatusRepository.countBooksByUserIdAndEndDateInMonth(user.getId(), currentMonth);
            Integer count = Integer.parseInt(countBook.toString());
            record.add(count);
        }
        return record;
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
