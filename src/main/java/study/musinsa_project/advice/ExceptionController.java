package study.musinsa_project.advice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.musinsa_project.service.exception.DeletionFailedException;
import study.musinsa_project.service.exception.ExpiredProductException;
import study.musinsa_project.service.exception.NotFoundException;
import study.musinsa_project.service.exception.UnauthorizedActionException;

@ControllerAdvice
@Slf4j
public class ExceptionController
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex)
    {
        log.error("DB 검색 결과 해당 에러를 반환합니다: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<String> handleUnauthorizedActionException(UnauthorizedActionException ex) {
        log.error("권한 문제 : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(DeletionFailedException.class)
    public ResponseEntity<String> handleDeletionFailedException(DeletionFailedException ex) {
        log.error("상품 삭제 실패: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(ExpiredProductException.class)
    public ResponseEntity<String> handleExpiredProductException(ExpiredProductException ex) {
        log.error("상품 만료: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
