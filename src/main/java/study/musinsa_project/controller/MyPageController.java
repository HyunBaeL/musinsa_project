package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.dto.MyPageCartResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.service.MyPageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
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
}
