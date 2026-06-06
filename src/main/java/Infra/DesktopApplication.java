package Infra;

import AppService.BookService;
import AppService.UserService;
import Repository.BookDAO; // <-- Adicione isto
import Repository.UserDAO;
import Views.LoginFrame;

public class DesktopApplication {
    public static void main(String[] args) {
        
        // Instancia os DAOs e Serviços manualmente
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        
        BookDAO bookDAO = new BookDAO(); // <-- Novo
        BookService bookService = new BookService(bookDAO); // <-- Passando o DAO para o serviço

        // Garante que a interface gráfica corre na Thread correta do Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(userService, bookService);
            loginFrame.setVisible(true);
        });
    }
}