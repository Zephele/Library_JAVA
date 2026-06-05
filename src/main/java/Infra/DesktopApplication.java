// ...existing code...
package Infra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import Views.LoginFrame; // Pasta que vamos criar no próximo passo

@SpringBootApplication(scanBasePackages = {"AppService", "Infra", "Repository", "Views"})
@EnableJpaRepositories(basePackages = "Repository")
public class DesktopApplication {
    public static void main(String[] args) {

        // Inicia o Spring Boot desativando o modo headless (necessário para o Swing funcionar)
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DesktopApplication.class)
                .headless(false)
                .run(args);

        // Garante que a interface gráfica corre na Thread correta do Swing (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Pede ao Spring a primeira tela e torna-a visível
            LoginFrame loginFrame = context.getBean(LoginFrame.class);
            loginFrame.setVisible(true);
        });
    }
}
// ...existing code...