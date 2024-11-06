package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.service.LoginService;
import study.musinsa_project.dto.Login;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public Map<String, String> login(@RequestBody Login loginRequest) {
        String token =  loginService.login(loginRequest);
        return Map.of("token", token);
    }
}
