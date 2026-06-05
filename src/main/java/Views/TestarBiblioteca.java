package Views;

import AppService.BookService; // Adicionado
import AppService.UserService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.SwingUtilities;

public class TestarBiblioteca {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(Infra.DesktopApplication.class)
                        .headless(false)
                        .run(args);

        UserService userService = context.getBean(UserService.class);
        // Pegando o BookService para a tela funcionar corretamente no teste
        BookService bookService = context.getBean(BookService.class); 

        // Passando os dois serviços
        SwingUtilities.invokeLater(() -> new BibliotecaFrame(true, userService, bookService));
    }
}