package Infra;

import AppService.BookService;
import AppService.UserService;
import Repository.BookDAO;
import Repository.UserDAO;
import Views.LoginFrame;

public class DesktopApplication {
    public static void main(String[] args) {
        
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        
        BookDAO bookDAO = new BookDAO();
        BookService bookService = new BookService(bookDAO);

        Repository.CategoriaDAO categoriaDAO = new Repository.CategoriaDAO();
        AppService.CategoriaService categoriaService = new AppService.CategoriaService(categoriaDAO);

        javax.swing.SwingUtilities.invokeLater(() -> {
            // Passe o categoriaService também!
            LoginFrame loginFrame = new LoginFrame(userService, bookService, categoriaService);
            loginFrame.setVisible(true);
        });
    }
}