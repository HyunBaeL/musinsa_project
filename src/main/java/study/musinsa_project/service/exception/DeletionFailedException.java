package study.musinsa_project.service.exception;

public class DeletionFailedException extends RuntimeException {
    public DeletionFailedException(String message) {
        super(message);
    }
}
