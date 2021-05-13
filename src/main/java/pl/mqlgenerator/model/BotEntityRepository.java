package pl.mqlgenerator.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BotEntityRepository extends JpaRepository<BotEntity, Integer> {

    @Override
    default void deleteById(Integer integer) {

    }
}
