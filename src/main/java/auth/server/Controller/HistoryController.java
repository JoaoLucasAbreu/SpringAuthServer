package auth.server.Controller;

import auth.server.Entity.History;
import auth.server.Repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @PostMapping
    public ResponseEntity<String> registrarLoginInvalido(@RequestParam String username) {
        History newLogin = new History(username, new Date());
        historyRepository.save(newLogin);
        return new ResponseEntity<>("Login inválido registrado", HttpStatus.CREATED);
    }
}