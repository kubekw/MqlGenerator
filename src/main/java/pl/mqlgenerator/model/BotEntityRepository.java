package pl.mqlgenerator.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BotEntityRepository extends JpaRepository<BotEntity, Integer> {


    public default void deleteBotEntityById(Integer id){


    }
}
