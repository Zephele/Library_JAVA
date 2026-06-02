package Views;

import AppService.UserService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class LoginFrame extends JFrame {

    private final UserService userService;

    public LoginFrame(UserService userService) {
        this.userService = userService; // O Spring injeta o serviço aqui automaticamente

        // Configurações básicas da Janela
        setTitle("Sistema ODS - Autenticação");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra no ecrã
        setLayout(new BorderLayout());

        // Criando o painel principal
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Campos de texto e Labels
        panel.add(new JLabel("Utilizador:"));
        JTextField txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Palavra-passe:"));
        JPasswordField txtPassword = new JPasswordField();
        panel.add(txtPassword);

        // Botão de Login
        JButton btnLogin = new JButton("Entrar");
        
        // Ação de clique no botão
        btnLogin.addActionListener((ActionEvent e) -> {
            String user = txtUsername.getText();
            // Aqui faríamos a validação. Para já, apenas testamos a injeção do serviço:
            int totalUsuarios = userService.listarTodos().size();
            
            JOptionPane.showMessageDialog(this, 
                "Tentativa de login: " + user + "\nTotal de utilizadores na base: " + totalUsuarios);
            
            // Futuramente, se a autenticação for bem-sucedida[cite: 51], 
            // fechamos este frame e abrimos o DashboardFrame.
        });

        // Adicionando tudo à janela
        add(panel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnLogin);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}