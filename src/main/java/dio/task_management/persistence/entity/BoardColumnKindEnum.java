package dio.task_management.persistence.entity;

import java.util.stream.Stream;

public enum BoardColumnKindEnum {
    INITIAL,
    FINAL,
    CANCEL,
    PENDING;

    public static BoardColumnKindEnum getByName(final String name) {
        return Stream.of(BoardColumnKindEnum.values())
                .filter(x -> x.name().equals(name))
                .findFirst().orElseThrow();
    }
}
