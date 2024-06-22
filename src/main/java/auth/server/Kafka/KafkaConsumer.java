package auth.server.Kafka;

import auth.server.Entity.History;
import auth.server.Repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaConsumer {
    @Autowired
    private HistoryRepository historyRepository;

    @KafkaListener(topics = "logins-invalidos", groupId = "login-invalido-group")
    public void consumeInvalidLogin(String username) {
        History novoLoginInvalido = new History(username, new Date());
        historyRepository.save(novoLoginInvalido);
    }
}
