package Views;

import AppService.BookService;
import AppService.UserService;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(UserService userService, BookService bookService) {
        setTitle("Dashboard - Relatório da Biblioteca");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        long totalUsuarios = userService.contarUsuarios();
        long totalLivros = bookService.contarLivros();

        JLabel titulo = new JLabel("Estatísticas do Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblUsuarios = new JLabel("📚 Total de Livros no Acervo: " + totalLivros, SwingConstants.CENTER);
        lblUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JLabel lblLivros = new JLabel("👥 Total de Utilizadores Registados: " + totalUsuarios, SwingConstants.CENTER);
        lblLivros.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Menção ao ODS 4 exigida no documento
        JLabel lblOds = new JLabel("Projeto alinhado ao ODS 4 da ONU - Educação de Qualidade", SwingConstants.CENTER);
        lblOds.setForeground(new Color(0, 105, 92));
        lblOds.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        add(titulo);
        add(lblLivros);
        add(lblUsuarios);
        add(lblOds);

        setVisible(true);
    }
}