package study.musinsa_project.exception.mypage;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();
    String getMessage();
    String getReason();
}
