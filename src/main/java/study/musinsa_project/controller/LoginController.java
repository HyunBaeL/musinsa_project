package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.Service.LoginService;
import study.musinsa_project.dto.Login;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {
        String message = loginService.login(loginRequest);
//        httpServletResponse.setHeader("X-AUTH-TOKEN" , token);
        return message;
    }
}
