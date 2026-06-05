package AppService;

import Infra.Entities.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPasswordAndIsActiveTrue(username, password);
        return user.isPresent();
    }
}