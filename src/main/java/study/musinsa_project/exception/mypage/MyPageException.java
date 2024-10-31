package study.musinsa_project.exception.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageException extends RuntimeException {

    private final CommonError commonError;
    private final String message;
}
