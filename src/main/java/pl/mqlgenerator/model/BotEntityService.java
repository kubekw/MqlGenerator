package pl.mqlgenerator.model;

import org.springframework.stereotype.Service;

@Service
public class BotEntityService {
    private final BotEntityRepository botEntityRepository;

    public BotEntityService(BotEntityRepository botEntityRepository) {
        this.botEntityRepository = botEntityRepository;
    }

    public void deleteBotById(Integer id){
        botEntityRepository.deleteById(id);
    }
}
