package Views;

import javax.swing.*;
import AppService.UserService;
import AppService.BookService;
import AppService.CategoriaService;
import java.awt.*;

public class CadastroFrame extends JFrame {

    private final UserService userService;
    private final BookService bookService;
    private final CategoriaService categoriaService;


    public CadastroFrame(UserService userService, BookService bookService, CategoriaService categoriaService) {
        this.userService = userService;
        this.bookService = bookService;
        this.categoriaService = categoriaService;

        setTitle("Cadastro");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel esquerda = new JPanel();
        esquerda.setBackground(new Color(0,121,107));
        esquerda.setPreferredSize(new Dimension(320,500));

        JLabel texto = new JLabel(" NOVO USUÁRIO");
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 24));

        esquerda.add(texto);

        JPanel direita = new JPanel();
        direita.setLayout(null);

        JLabel titulo = new JLabel("Cadastro");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBounds(180,40,200,40);

        JTextField usuario = new JTextField();
        usuario.setBorder(BorderFactory.createTitledBorder("Usuário"));
        usuario.setBounds(100,110,300,50);

        JPasswordField senha = new JPasswordField();
        senha.setBorder(BorderFactory.createTitledBorder("Senha"));
        senha.setBounds(100,180,300,50);

        JPasswordField confirmar = new JPasswordField();
        confirmar.setBorder(BorderFactory.createTitledBorder("Confirmar Senha"));
        confirmar.setBounds(100,250,300,50);

        JButton salvar = new JButton("Cadastrar");
        salvar.setBounds(100,330,300,40);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(100,380,300,40);

        salvar.addActionListener(e -> {
            String user = usuario.getText().trim();
            String pass = new String(senha.getPassword()).trim();
            String conf = new String(confirmar.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pass.equals(conf)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                userService.cadastrar(user, pass);
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                new LoginFrame(userService, bookService, categoriaService);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar no banco: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }
        });

        voltar.addActionListener(e -> {
            new LoginFrame(userService, bookService, categoriaService);
            dispose();
        });

        direita.add(titulo);
        direita.add(usuario);
        direita.add(senha);
        direita.add(confirmar);
        direita.add(salvar);
        direita.add(voltar);

        add(esquerda, BorderLayout.WEST);
        add(direita, BorderLayout.CENTER);

        setVisible(true);
    }
}