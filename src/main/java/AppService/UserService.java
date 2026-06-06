package AppService;

import Infra.Entities.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Cria o admin automaticamente quando a aplicação arranca pela primeira vez
    @PostConstruct
    public void initAdmin() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setName("Administrador");
            admin.setAdmin(true);
            userRepository.save(admin);
        }
    }

    // Em vez de devolver boolean, devolve o utilizador para sabermos se é admin
    public User autenticar(String username, String password) {
        return userRepository.findByUsernameAndPasswordAndIsActiveTrue(username, password).orElse(null);
    }

    public void cadastrar(String username, String password) {
        User novoUsuario = new User();
        novoUsuario.setUsername(username);
        novoUsuario.setPassword(password);
        novoUsuario.setName(username);
        novoUsuario.setAdmin(false); // Por padrão, quem se regista é utilizador comum
        userRepository.save(novoUsuario);
    }
    
    // NOVO: Para o Dashboard
    public long contarUsuarios() {
        return userRepository.count();
    }
}