package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.service.LoginService;
import study.musinsa_project.dto.Login;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest) {
        String message = loginService.login(loginRequest);
        return "로그인 성공! 토큰:" + message;
    }
}
