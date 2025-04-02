package dio.task_management.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BoardColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "`order`", nullable = false)
    private int order;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardColumnKindEnum kind;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board; // pega meu board
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CardEntity> cards = new ArrayList<>();

    public void addCard(final CardEntity card) {
        card.setBoardColumn(this);
        cards.add(card);
    }
}
