package auth.server.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import auth.server.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    Iterable<User>  findByIsBlockedTrue();

    // Iterable<User> findByUsernameAndPassword(String username, String senha);
}