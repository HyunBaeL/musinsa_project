package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.musinsa_project.dto.MyPageCartResponse;
import study.musinsa_project.dto.MyPageUserDetailResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.dto.MyPageUserUpdateRequest;
import study.musinsa_project.service.MyPageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/myPage/{userId}")
    public ResponseEntity<MyPageUserResponse> myPage(@PathVariable int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPage(userId));
    }

    @GetMapping("/myPageDetail/{userId}")
    public ResponseEntity<MyPageUserDetailResponse> myPageDetail(@PathVariable int userId){
        return ResponseEntity.ok(myPageService.myPageUserDetail(userId));
    }

    @PostMapping("/myPageUserUpdate/{userId}")
    public ResponseEntity<MyPageUserDetailResponse> myPageUserUpdate(
            @PathVariable int userId, @RequestBody MyPageUserUpdateRequest myPageUserUpdateRequest){
        return myPageService.myPageUserUpdate(userId,myPageUserUpdateRequest);
    }

    @GetMapping("/myPageCart/{userId}")
    public ResponseEntity<MyPageCartResponse> myPageCart(@PathVariable int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPageCart(userId));
    }

    @PutMapping("/myPageImageUpload/{userId}")
    public ResponseEntity<?> myPageImageUpload(@RequestPart(value = "image", required = false) MultipartFile image, @PathVariable int userId){
        return ResponseEntity.ok(myPageService.upload(image,userId));
    }
}
