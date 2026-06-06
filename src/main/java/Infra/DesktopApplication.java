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

        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(userService, bookService);
            loginFrame.setVisible(true);
        });
    }
}