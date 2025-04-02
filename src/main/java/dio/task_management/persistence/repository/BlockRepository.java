package dio.task_management.persistence.repository;

import dio.task_management.persistence.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
}
