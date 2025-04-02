package dio.task_management.ui;

import dio.task_management.exceptions.BoardColumnNotFoundException;
import dio.task_management.exceptions.CardNotFoundException;
import dio.task_management.persistence.entity.*;
import dio.task_management.services.BlockService;
import dio.task_management.services.BoardService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardService boardService;
    private final BlockService blockService;
    private BoardEntity board;

    public BoardMenu(BoardService boardService, BlockService blockService) {
        this.boardService = boardService;
        this.blockService = blockService;
    }

    public void execute(final BoardEntity board) {
        try {
            this.board = board;
            System.out.printf("Bem vindo ao board %s. \nFavor, selecione a operação desejada! \n", board.getId());
            var option = 1;
            while (option != 9) {
                System.out.println(" 1 - Ciar um novo card");
                System.out.println(" 2 - Mover um card");
                System.out.println(" 3 - Bloquear um card");
                System.out.println(" 4 - Desbloquear um card");
                System.out.println(" 5 - Cancelar um card");
                System.out.println(" 6 - Visualizar colunas");
                System.out.println(" 7 - Visualizar colunas com cards");
                System.out.println(" 8 - Visualizar card");
                System.out.println(" 9 - Voltar ao menu anterior");
                System.out.println(" 10 - Sair");
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showColumn();
                    case 7 -> showColumnWithCard();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando para o menu anterior...");
                    case 10 -> System.exit(0);
                    default -> System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCard() {
        var card = new CardEntity();
        System.out.println("Informe o titulo do card!");
        card.setTitle(scanner.next());
        System.out.println("Informe o descrição do card!");
        card.setDescription(scanner.next());
        board.getInitialColumn().addCard(card);
        boardService.update(board);
        System.out.println("Card criado com sucesso!");
    }

    private void moveCardToNextColumn() {
        var columns = board.getBoardColumns();
        printColumns(columns);
        System.out.printf("Id da coluna: ");
        var columnId = scanner.nextLong();
        var cards = columns.stream()
                .filter(x -> x.getId().equals(columnId))
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new)
                .getCards(); // coleta os cards
        printCards(cards);

        System.out.printf("Id do card a ser movido: ");
        var cardId = scanner.nextLong();
        System.out.printf("Id da coluna destino do card: ");
        printColumns(columns);
        var columnDestineId = scanner.nextLong();
        moveCard(cardId, columnDestineId);
        System.out.println("Card cancelado com sucesso!");
    }

    private void blockCard() {
        var columns = board.getBoardColumns();
        printColumns(columns);
        System.out.printf("Id da coluna a qual pertence o card: ");
        var selectedColumn = scanner.nextLong();
        var column = columns
                .stream()
                .filter(x -> x.getId().equals(selectedColumn))
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new);
        System.out.printf("Id do card a ser bloqueado: ");
        var cardId = scanner.nextLong();
        System.out.printf("Motivo do bloqueio: ");
        var reason = scanner.next();
        var block = new BlockEntity();
        block.setBlockedReason(reason);
        var card = column.getCards().stream().filter(x -> x.getId().equals(cardId))
                .findFirst()
                .orElseThrow(CardNotFoundException::new);
        if(card.isBlocked()) {
            System.out.println("Card já está bloqueado!");
        } else {
            block.setBlockedAt(OffsetDateTime.now());
            block.setCard(card);
            blockService.update(block);
            board.getBoardColumns();
            updateBoard();
            System.out.println("Card bloqueado com sucesso!");
        }
    }

    private void unblockCard() {
        var columns = board.getBoardColumns();
        printColumns(columns);
        System.out.printf("Id da coluna a qual pertence o card: ");
        var selectedColumn = scanner.nextLong();
        var cards = columns
                .stream()
                .filter(x -> x.getId().equals(selectedColumn))
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new)
                .getCards();
        printCards(cards);
        System.out.printf("Digite o id do card a ser desbloqueado: ");
        var cardSelected = scanner.nextLong();
        var block = blockService.findByCardId(cardSelected);
        System.out.printf("Motivo do desbloqueio: ");
        var unblockReason = scanner.next();
        block.setUnblockedReason(unblockReason);
        block.setUnblockedAt(OffsetDateTime.now());
        blockService.update(block);
        updateBoard();
    }

    private void cancelCard() {
        var columns = board.getBoardColumns();
        printColumns(columns);
        System.out.printf("Id da coluna: ");
        var columnId = scanner.nextLong();
        var column = columns.stream()
                .filter(x -> x.getId().equals(columnId))
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new);
        var cards = column.getCards();
        printCards(cards);
        System.out.printf("Id do card a ser cancelado: ");
        var cardId = scanner.nextLong();
        moveCard(cardId, board.getCanceledColumn().getId());
        System.out.println("Card cancelado com sucesso!");
    }

    private void moveCard(final long cardId, final long columnDestineId) {
        var card = board.getBoardColumns()
                .stream()
                .map(x -> x.getCards()) // Transforma cada boardColumns em sua lista de cards
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .stream().filter(x -> x.getId().equals(cardId))
                .findFirst()
                .orElseThrow(CardNotFoundException::new);
        board.getBoardColumns()
                .stream()
                .filter(x -> x.getId().equals(columnDestineId))
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new)
                .addCard(card); // Adiciona o card em outra coluna, assim atualizando a coluna
        boardService.update(board);
    }

    private void showColumnWithCard() {
        var boardColumns = board.getBoardColumns()
                .stream()
                .filter(x -> !x.getCards().isEmpty())
                .toList();
        printColumns(boardColumns);
    }

    private void showColumn() {
        var boardColumns = board.getBoardColumns();
        printColumns(boardColumns);
    }

    private void showCard() {
        var boardColumns = board.getBoardColumns();
        printColumns(boardColumns);
        System.out.printf("Id da coluna: ");
        var columnId = scanner.nextLong();
        var column = boardColumns
                .stream()
                .filter(x -> x.getId().equals(columnId))
                .findFirst()
                .orElseThrow(CardNotFoundException::new);
        printCards(column.getCards());
        System.out.printf("Id do card: ");
        var cardId = scanner.nextLong();
        var cards = boardColumns
                .stream()
                .map(x -> x
                        .getCards()
                        .stream()
                        .filter(y -> y.getId().equals(cardId))
                        .findFirst().orElse(null))
                .toList();
        printCards(cards);
    }

    private void printColumns(List<BoardColumnEntity> columns) {
        for (var column : columns) {
            if(column == null) return;
            System.out.printf("Id: %s - Nome: %s \n", column.getId(), column.getName());
        }
    }

    private void printCards(List<CardEntity> cards) {
        for (var card : cards) {
            if(card == null) return;
            System.out.printf("Id: %s - Titulo: %s - Descrição: %s - Bloqueado %s vezes - ", card.getId(), card.getTitle(), card.getDescription(), card.getBlockCount());
            System.out.printf(card.isBlocked() ? "Card bloqueado\n" : "Card ativo \n"); // os print se juntam
        }
    }

    private void updateBoard() {
        this.board = boardService.findById(board.getId());
    }
}
