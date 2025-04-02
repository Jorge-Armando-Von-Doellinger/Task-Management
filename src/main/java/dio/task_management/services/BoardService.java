package dio.task_management.services;

import dio.task_management.exceptions.BoardNotFoundedException;
import dio.task_management.persistence.entity.BoardEntity;
import dio.task_management.persistence.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public void update(final BoardEntity entity) {
        entity.getBoardColumns();
        repository.save(entity);
    }
    @Transactional
    public BoardEntity findById(final long id) {
        return repository.findById(id).orElseThrow(BoardNotFoundedException::new);
    }
    @Transactional
    public Optional<List<BoardEntity>> findAll() {
        return Optional.of(repository.findAll());
    }
    @Transactional
    public void delete(final long id) {
        repository.delete(repository.findById(id).orElseThrow(BoardNotFoundedException::new));
    }
}
