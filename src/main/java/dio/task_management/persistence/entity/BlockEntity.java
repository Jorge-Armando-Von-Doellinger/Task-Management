package dio.task_management.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime blockedAt;
    @Column(nullable = false)
    private String blockedReason;
    private OffsetDateTime unblockedAt;
    private String unblockedReason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;
}
