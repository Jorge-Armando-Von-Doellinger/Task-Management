package dio.task_management.persistence.repository;

import dio.task_management.persistence.entity.BoardColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumnEntity, Long> {
}
