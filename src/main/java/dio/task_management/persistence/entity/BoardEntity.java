package dio.task_management.persistence.entity;

import dio.task_management.exceptions.BoardColumnNotFoundException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "BOARDS")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getInitialColumn() {
        return this.getByKind(BoardColumnKindEnum.INITIAL)
                .stream()
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new);
    }
    public BoardColumnEntity getCanceledColumn() {
        return this.getByKind(BoardColumnKindEnum.CANCEL)
                .stream()
                .findFirst()
                .orElseThrow(BoardColumnNotFoundException::new);
    }
    public List<BoardColumnEntity> getByKind(final BoardColumnKindEnum kind) {
        return this.getBoardColumns().stream()
                .filter(bc -> bc.getKind().equals(kind))
                .toList();
    }
    public void addColumn(final BoardColumnEntity column) {
        column.setBoard(this);
        boardColumns.add(column);
    }
}
