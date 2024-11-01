package study.musinsa_project.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.musinsa_project.dto.ErrorMyPageResponse;
import study.musinsa_project.exception.mypage.CommonError;
import study.musinsa_project.exception.mypage.MyPageException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<ErrorMyPageResponse> handleMyPageException(MyPageException exception) {

        CommonError error = exception.getCommonError();

        ErrorMyPageResponse errorResponse = ErrorMyPageResponse.builder()
                .status(error.getStatus())
                .message(exception.getMessage())
                .reason(error.getReason())
                .build();

        return ResponseEntity.status(error.getStatus()).body(errorResponse);
    }
}
