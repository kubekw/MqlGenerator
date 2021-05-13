package pl.mqlgenerator.model;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mqlgenerator.model.BotList;

public interface BotListRepository extends JpaRepository<BotList, Long> {
}
