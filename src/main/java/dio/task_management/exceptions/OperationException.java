package dio.task_management.exceptions;

public class OperationException extends RuntimeException {
    public OperationException(String message) {
        super(message);
    }
    public OperationException() {
        super("Houve um erro inesperado durante esta operação! Verifique os dados digitados e tente novamente!");
    }
}
