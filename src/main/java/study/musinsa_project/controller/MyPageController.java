package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.musinsa_project.dto.MyPageCartResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.service.MyPageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/myPage")
    public ResponseEntity<MyPageUserResponse> myPage(@RequestParam int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPage(userId));
    }

    @GetMapping("/myPageCart")
    public ResponseEntity<MyPageCartResponse> myPageCart(@RequestParam int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPageCart(userId));
    }

    @PostMapping("/myPageImageUpload")
    public ResponseEntity<?> myPageImageUpload(@RequestPart(value = "image", required = false) MultipartFile image, @RequestParam int userId){
        return ResponseEntity.ok(myPageService.upload(image,userId));
    }
}
