package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.config.security.JwtTokenProvider;
import study.musinsa_project.dto.Withdraw;
import study.musinsa_project.service.WithdrawService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WithdrawController {
    private final WithdrawService withdrawService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody Withdraw withdraw) {
        String token = withdraw.getToken();
        // Token에서 username 추출
        String username = jwtTokenProvider.getSubFromToken(token);
        withdrawService.withdrawUser(username);
        return username + " 회원 탈퇴하였습니다.";
    }
}
