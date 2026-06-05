package Views;

import AppService.UserService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class LoginFrame extends JFrame {

    private final UserService userService;

    public LoginFrame(UserService userService) {
        this.userService = userService;

        setTitle("Biblioteca Digital");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel esquerda = new JPanel();
        esquerda.setBackground(new Color(0, 121, 107));
        esquerda.setPreferredSize(new Dimension(320, 500));
        esquerda.setLayout(new GridBagLayout());

        JLabel logo = new JLabel(" BIBLIOTECA");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        esquerda.add(logo);

        JPanel direita = new JPanel();
        direita.setLayout(null);
        direita.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Login");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBounds(180, 60, 200, 40);

        JTextField usuario = new JTextField();
        usuario.setBorder(BorderFactory.createTitledBorder("Usuário"));
        usuario.setBounds(100, 140, 300, 55);

        JPasswordField senha = new JPasswordField();
        senha.setBorder(BorderFactory.createTitledBorder("Senha"));
        senha.setBounds(100, 220, 300, 55);

        JButton entrar = new JButton("Entrar");
        entrar.setBounds(100, 310, 300, 45);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setBounds(100, 370, 300, 45);

        entrar.setBackground(new Color(0,121,107));
        entrar.setForeground(Color.WHITE);

        cadastrar.setBackground(new Color(33,150,243));
        cadastrar.setForeground(Color.WHITE);

        entrar.addActionListener(e -> {
            String user = usuario.getText().trim();
            String pass = new String(senha.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha usuário e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (user.equalsIgnoreCase("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login de Administrador");
                new BibliotecaFrame(true, userService);
                dispose();
                return;
            }

            if (userService.login(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login realizado.");
                new BibliotecaFrame(false, userService);
                dispose();
                return;
            }

            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        });

        cadastrar.addActionListener(e -> {
            new CadastroFrame(userService);
            dispose();
        });

        direita.add(titulo);
        direita.add(usuario);
        direita.add(senha);
        direita.add(entrar);
        direita.add(cadastrar);

        add(esquerda, BorderLayout.WEST);
        add(direita, BorderLayout.CENTER);

        setVisible(true);
    }
}