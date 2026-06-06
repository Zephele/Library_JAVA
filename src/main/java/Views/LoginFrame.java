package Views;

import AppService.UserService;
import Infra.Entities.User;
import AppService.BookService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final UserService userService;
    private final BookService bookService;

    public LoginFrame(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;

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

// Substitua o bloco do "entrar.addActionListener" por este:
        entrar.addActionListener(e -> {
            String user = usuario.getText().trim();
            String pass = new String(senha.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha utilizador e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Autenticação real pela base de dados
                User utilizadorLogado = userService.autenticar(user, pass);
                
                if (utilizadorLogado != null) {
                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso.");
                    // Passamos se ele é admin ou não lendo do objeto
                    new BibliotecaFrame(utilizadorLogado.isAdmin(), userService, bookService);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Utilizador ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Falha de conexão:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cadastrar.addActionListener(e -> {
            new CadastroFrame(userService, bookService); // <-- Precisa enviar os dois!
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