package dio.task_management.services;

import dio.task_management.exceptions.CardNotFoundException;
import dio.task_management.persistence.entity.BlockEntity;
import dio.task_management.persistence.repository.BlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlockService {
    private final BlockRepository repository;

    public BlockService(BlockRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void update(final BlockEntity block) {
        repository.save(block);
    }

    @Transactional
    public Optional<List<BlockEntity>> findAll() {
        return Optional.of(repository.findAll());
    }
    @Transactional
    public BlockEntity findByCardId(final long cardId) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getCard().equals(cardId))
                .findFirst()
                .orElseThrow(CardNotFoundException::new); // Ja que estava procurando pelo card, nao pelo id do block
    }
}
