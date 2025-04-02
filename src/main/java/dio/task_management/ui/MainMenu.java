package dio.task_management.ui;

import dio.task_management.persistence.entity.BoardColumnEntity;
import dio.task_management.persistence.entity.BoardColumnKindEnum;
import dio.task_management.persistence.entity.BoardEntity;
import dio.task_management.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class MainMenu implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardMenu boardMenu;
    private final BoardService boardService;

    public MainMenu(BoardMenu boardMenu, BoardService boardService) {
        this.boardMenu = boardMenu;
        this.boardService = boardService;
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void execute()  {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada!");
        var option = -1;
        while (true) {
            System.out.println(" 1 - Mostrar boards");
            System.out.println(" 2 - Ciar um novo board");
            System.out.println(" 3 - Selecioanr um board existente");
            System.out.println(" 4 - Excluir um board");
            System.out.println(" 5 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> showBoards();
                case 2 -> createBoard();
                case 3 -> selectBoard();
                case 4 -> deleteBoard();
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void showBoards() {
        var boards = boardService.findAll();
        for (var board : boards.get()) {
            System.out.printf("Id: %s - Nome: %s \n", board.getId(), board.getName());
        }
    }

    private void createBoard() {
        var board = new BoardEntity();
        System.out.printf("Nome do novo board: ");
        board.setName(scanner.next());
        createColumns(board);
        boardService.update(board);
        System.out.println("Board criado com sucesso!");
    }

    private void createColumns(final BoardEntity board) {
        // List<BoardColumnEntity> columns = new ArrayList<>();
        System.out.printf("Nome da coluna inicial: ");
        var initialColumnName = scanner.next();
        board.addColumn(generateColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0));
        System.out.printf("Quantidade de colunas extras: ");
        var additionalColumns =  scanner.nextInt();
        for (var i = 0; i < additionalColumns; i++) {
            System.out.printf("Nome da coluna extra: ");
            var additionalColumnName = scanner.next();
            board.addColumn(generateColumn(additionalColumnName, BoardColumnKindEnum.PENDING, i + 1));
        }
        System.out.printf("Nome da coluna final: ");
        var finalColumnName = scanner.next();
        board.addColumn(generateColumn(finalColumnName, BoardColumnKindEnum.FINAL, additionalColumns + 1));
        System.out.printf("Nome da coluna de cancelamento: ");
        var cancelColumnName = scanner.next();
        board.addColumn(generateColumn(finalColumnName, BoardColumnKindEnum.CANCEL, additionalColumns + 2));
    }

    private void selectBoard() {
        showBoards();
        System.out.printf("Selecione o id do board: ");
        long boardId = scanner.nextLong();
        var board = boardService.findById(boardId);
        boardMenu.execute(board);
    }

    private void deleteBoard() {
        System.out.printf("Id do board a ser excluído: ");
        long id = scanner.nextLong();
        boardService.delete(id);
    }
    private BoardColumnEntity generateColumn(final String name, final BoardColumnKindEnum kind, int order) {
        var column = new BoardColumnEntity();
        column.setName(name);
        column.setKind(kind);
        column.setOrder(order);
        return column;
    }
}
