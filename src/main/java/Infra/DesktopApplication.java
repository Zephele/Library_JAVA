package Infra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import Views.LoginFrame;

@SpringBootApplication(scanBasePackages = {"AppService", "Infra", "Repository", "Views"})
@EnableJpaRepositories(basePackages = "Repository")
public class DesktopApplication {
    public static void main(String[] args) {

        // Garante suporte a janelas (Swing) antes do Spring iniciar
        System.setProperty("java.awt.headless", "false");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DesktopApplication.class)
                .headless(false)
                .run(args);

        // Garante que a interface gráfica corre na Thread correta do Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = context.getBean(LoginFrame.class);
            loginFrame.setVisible(true);
        });
    }
}