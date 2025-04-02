package dio.task_management.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_column_id")
    private BoardColumnEntity boardColumn;
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<BlockEntity> blocks;


    public boolean isBlocked() {
        var block = blocks
                .stream()
                .filter(b -> b.getUnblockedAt() == null)
                .findFirst()
                .orElse(null); // Pega todos que nao houve desbloqueio
        return block != null; // Se block é null, significa que não teve um ultimo bloqueio
    }

    public BlockEntity getLastBlock() {
        return blocks
                .stream()
                .filter(b -> b.getUnblockedAt() == null)
                .findFirst()
                .orElse(null);
    }

    public long getBlockCount() {
        return blocks.isEmpty() ? (long) 0 : blocks.stream().count();
    }

}
