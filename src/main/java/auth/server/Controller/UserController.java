package auth.server.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auth.server.Dto.UserCreateDto;
import auth.server.Dto.UserChangePasswordDto;
import auth.server.Entity.User;
import auth.server.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping()
  public ResponseEntity<String> createUser(@RequestBody UserCreateDto user) {

    if (userRepository.findByUsername(user.getUsername()) != null) {
      return new ResponseEntity<String>("Usuário já existe", HttpStatus.BAD_REQUEST);
    }

    User createdUser = new User();
    createdUser.setUsername(user.getUsername());
    createdUser.setPassword(user.getPassword());

    // logger?
    userRepository.save(createdUser);
    return new ResponseEntity<String>(createdUser.getUsername() + " criado com sucesso!", HttpStatus.CREATED);
  }

  @PutMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserCreateDto user) {

    User fetchedUser = userRepository.findByUsername(user.getUsername());
    if (fetchedUser == null) {
      return new ResponseEntity<String>("Usuário não cadastrado", HttpStatus.BAD_REQUEST);
    }

    if (fetchedUser.getIsBlocked()) {
      return new ResponseEntity<String>("Usuário está bloqueado", HttpStatus.BAD_REQUEST);
    }

    if (user.getPassword().equals(fetchedUser.getPassword())) {
      if (fetchedUser.getTotalLogins() > 10) {
        return new ResponseEntity<String>("Usuário precisa trocar a senha", HttpStatus.FORBIDDEN);
      }

      fetchedUser.setTotalLogins(fetchedUser.getTotalLogins() + 1);
      userRepository.save(fetchedUser);
      return new ResponseEntity<String>("Usuário logado", HttpStatus.OK);

    } else {
      if (fetchedUser.getTotalFails() > 5) {
        // fetchedUser.setTotalFails(fetchedUser.getTotalFails() + 1);
        fetchedUser.setIsBlocked(true);
        userRepository.save(fetchedUser);
        return new ResponseEntity<String>("Usuário bloqueado", HttpStatus.BAD_REQUEST);

      } else {
        fetchedUser.setTotalFails(fetchedUser.getTotalFails() + 1);
        userRepository.save(fetchedUser);
        return new ResponseEntity<String>("Usuário com falha no login", HttpStatus.BAD_REQUEST);

      }
    }
  }

  @PutMapping("/changePassword")
  public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto user) {

    User fetchedUser = userRepository.findByUsername(user.getUsername());
    if (fetchedUser == null) {
      return new ResponseEntity<String>("Usuário não cadastrado", HttpStatus.BAD_REQUEST);
    }

    if (!fetchedUser.getPassword().equals(user.getOldPassword())) {
      return new ResponseEntity<String>("Senha antiga errada", HttpStatus.BAD_REQUEST);
    }

    fetchedUser.setPassword(user.getNewPassword());
    fetchedUser.setTotalLogins(0);
    userRepository.save(fetchedUser);
    return new ResponseEntity<String>("Senha nova ataualizada", HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<Iterable<User>> fetchAllUsers() {
    Iterable<User> fetchedUsers = userRepository.findAll();
    return new ResponseEntity<Iterable<User>>(fetchedUsers, HttpStatus.OK);
  }

  @GetMapping("/blocked")
  public ResponseEntity<Iterable<User>> fetchAllBlockedUsers() {
    Iterable<User> fetchedUsers = userRepository.findByIsBlockedTrue();
    return new ResponseEntity<Iterable<User>>(fetchedUsers, HttpStatus.OK);
  }
}
