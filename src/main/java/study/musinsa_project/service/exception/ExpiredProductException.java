package study.musinsa_project.service.exception;

public class ExpiredProductException extends RuntimeException {
    public ExpiredProductException(String message) {
        super(message);
    }
}
