package smpl.oauth2.mapper;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Generic interface for mapping one entity to another.
 *
 * @param <A> source entity
 * @param <B> target entity
 */
public interface Mapper<A, B> {

  B map(A source);

  default List<B> map(List<A> source) {

    return Optional.ofNullable(source)
        .orElse(emptyList())
        .stream()
        .filter(Objects::nonNull)
        .map(this::map)
        .collect(Collectors.toList());
  }
}
