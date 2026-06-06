package AppService;

import Infra.Entities.User;
import Repository.UserDAO;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        initAdmin(); // Tenta criar o admin assim que o serviço inicia
    }

    public void initAdmin() {
        // Se a base não tiver o admin, ele recria
        if (userDAO.autenticar("admin", "123") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setName("Administrador");
            admin.setAdmin(true);
            admin.setActive(true);
            userDAO.cadastrar(admin);
        }
    }

    public User autenticar(String username, String password) {
        return userDAO.autenticar(username, password);
    }

    public void cadastrar(String username, String password) {
        User novoUsuario = new User();
        novoUsuario.setUsername(username);
        novoUsuario.setPassword(password);
        novoUsuario.setName(username);
        novoUsuario.setAdmin(false);
        novoUsuario.setActive(true);
        userDAO.cadastrar(novoUsuario);
    }

    public long contarUsuarios() {
        return 0; // Pode implementar o SQL COUNT no UserDAO futuramente
    }
}