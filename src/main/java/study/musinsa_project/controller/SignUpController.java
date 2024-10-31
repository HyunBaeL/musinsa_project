package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.musinsa_project.Service.SignUpService;
import study.musinsa_project.dto.SignUp;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignUp signUpRequest){
        String message = signUpService.signUp(signUpRequest);
        return message;
    }

}
