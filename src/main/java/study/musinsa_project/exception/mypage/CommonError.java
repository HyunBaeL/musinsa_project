package study.musinsa_project.exception.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonError implements ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "올바른 요청인지 확인 해 주세요."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다.", "접근 권한을 확인 해 주세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"요청한 리소스를 찾을 수 없습니다", "URL,유효한 데이터인지 확인 해 주세요."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드 입니다.", "GET,PUT 등 올바른 요청인지 확인 해 주세요."),
    CONFLICT(HttpStatus.CONFLICT, "데이터 충돌이 발생 했습니다.", "존재하는 데이터인지 확인 해 주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 내부 오류입니다.","데이터베이스 이상이 없는지 확인 해 주세요."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,"해당 서비스를 이용할 수 없습니다.","관리자에게 문의 해 주세요."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"사용자를 찾을 수 없습니다.","정보를 다시 확인해 주세요."),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "파일이 비어있습니다.","파일을 다시 업로드 해 주세요."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "파일 확장자가 유효하지 않습니다.", "jpg, jpeg, png, gif 확장자만 허용됩니다.");

    private final HttpStatus status;
    private final String message;
    private final String reason;

}
