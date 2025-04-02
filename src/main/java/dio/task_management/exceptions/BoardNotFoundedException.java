package dio.task_management.exceptions;

public class BoardNotFoundedException extends RuntimeException {
    public BoardNotFoundedException(String message) {
        super(message);
    }
    public BoardNotFoundedException() {
        super("Board não encontrado! Favor, valide os dados digitados e tente novamente!");
    }

}
