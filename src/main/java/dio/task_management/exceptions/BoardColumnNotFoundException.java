package dio.task_management.exceptions;

public class BoardColumnNotFoundException extends RuntimeException {
    public BoardColumnNotFoundException(String message) {
        super(message);
    }
    public BoardColumnNotFoundException() {
        super("Coluna não encontrada! Verifique os dados digitados e tente novamente!");
    }
}
