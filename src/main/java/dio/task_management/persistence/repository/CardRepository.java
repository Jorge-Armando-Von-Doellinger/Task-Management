package dio.task_management.persistence.repository;

import dio.task_management.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
