package dio.task_management.exceptions;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
    public CardNotFoundException() {
        super("Card não encontrado! Verifique os dados digitados e tente novamente!");
    }
}
