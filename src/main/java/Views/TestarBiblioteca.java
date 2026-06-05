package Views;

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

        SwingUtilities.invokeLater(() -> new BibliotecaFrame(true, userService));
    }
}