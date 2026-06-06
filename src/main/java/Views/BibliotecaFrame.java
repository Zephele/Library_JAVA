package Views;

import javax.swing.*;
import AppService.UserService;
import AppService.BookService;
import Infra.Entities.Book;
import AppService.CategoriaService;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BibliotecaFrame extends JFrame {

    private final UserService userService;
    private final BookService bookService;
    private JPanel painelLivros;
    private JLabel contadorLivros;
    private boolean admin;
    private final CategoriaService categoriaService;

    public BibliotecaFrame(boolean admin, UserService userService, BookService bookService, CategoriaService categoriaService) {

        this.admin = admin;
        this.userService = userService;
        this.bookService = bookService;
        this.categoriaService = categoriaService;

        setTitle("Biblioteca Digital");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setBackground(new Color(0, 105, 92));
        menu.setPreferredSize(new Dimension(230, 700));
        menu.setLayout(new GridLayout(8, 1, 15, 15));

        JLabel logo = new JLabel(" Biblioteca");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JButton adicionar = new JButton(" Adicionar Livro");
        JButton sair = new JButton(" Sair");

        adicionar.setBackground(new Color(46, 125, 50));
        adicionar.setForeground(Color.WHITE);

        sair.setBackground(new Color(198, 40, 40));
        sair.setForeground(Color.WHITE);

        JButton btnDashboard = new JButton(" Dashboard");
        btnDashboard.setBackground(new Color(0, 150, 136));
        btnDashboard.setForeground(Color.WHITE);
        
        JButton btnCategorias = new JButton(" Categorias");
        adicionar.setBackground(new Color(46, 125, 50)); adicionar.setForeground(Color.WHITE);
        sair.setBackground(new Color(198, 40, 40)); sair.setForeground(Color.WHITE);
        btnDashboard.setBackground(new Color(0, 150, 136)); btnDashboard.setForeground(Color.WHITE);
        btnCategorias.setBackground(new Color(25, 118, 210)); btnCategorias.setForeground(Color.WHITE);

        if (!admin) {
            adicionar.setVisible(false);
            btnDashboard.setVisible(false);
            btnCategorias.setVisible(false);
        }
        
        menu.add(logo);
        menu.add(new JLabel());
        menu.add(btnDashboard);
        menu.add(btnCategorias);
        menu.add(adicionar);
        menu.add(sair);

        btnCategorias.addActionListener(e -> {
            new GestaoCategoriasFrame(categoriaService);
        });

        btnDashboard.addActionListener(e -> {
            new DashboardFrame(userService, bookService);
        });

        menu.add(logo);
        menu.add(new JLabel());
        menu.add(adicionar);
        menu.add(sair);

        sair.addActionListener(e -> {
            LoginFrame login = new LoginFrame(userService, bookService, categoriaService);
            login.setVisible(true);
            dispose();
        });

        JPanel topo = new JPanel(new BorderLayout());
        JLabel tituloPrincipal = new JLabel("Biblioteca Digital");
        tituloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        topo.add(tituloPrincipal, BorderLayout.WEST);

        painelLivros = new JPanel();
        painelLivros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelLivros.setLayout(new GridLayout(0, 3, 20, 20)); 

        JScrollPane scrollPane = new JScrollPane(painelLivros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contadorLivros = new JLabel("Total de Livros: 0");

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(scrollPane, BorderLayout.CENTER);
        centro.add(contadorLivros, BorderLayout.SOUTH);

        atualizarPainelLivros();

        adicionar.addActionListener(e -> {

            String nome = JOptionPane.showInputDialog(this, "Nome do Livro:");
            if (nome == null || nome.trim().isEmpty()) return;

            String autor = JOptionPane.showInputDialog(this, "Autor:");
            if (autor == null || autor.trim().isEmpty()) return;

            String ano = JOptionPane.showInputDialog(this, "Ano:");
            if (ano == null || ano.trim().isEmpty()) return;

            String descricao = JOptionPane.showInputDialog(this, "Descrição:");
            if (descricao == null || descricao.trim().isEmpty()) return;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a capa do livro");
            
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens (*.jpg, *.png)", "jpg", "jpeg", "png"));

            String caminhoImagem = "/imagens/domcasmurro.jpg";
            
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                caminhoImagem = fileChooser.getSelectedFile().getAbsolutePath();
            }

            bookService.salvarLivro(nome, autor, ano, descricao, caminhoImagem);

            atualizarPainelLivros();
        });
        add(menu, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    private void atualizarPainelLivros() {
        painelLivros.removeAll();
        
        List<Book> livrosNoBanco = bookService.listarTodos();
        
        for (Book livro : livrosNoBanco) {
            painelLivros.add(criarCard(livro));
        }
        
        painelLivros.revalidate();
        painelLivros.repaint();
        contadorLivros.setText("Total de Livros: " + livrosNoBanco.size());
    }

    private JPanel criarCard(Book livro) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        ImageIcon icon = carregarImagem(livro.getCaminhoImagem());
        Image imagem = icon.getImage().getScaledInstance(140, 190, Image.SCALE_SMOOTH);

        JLabel capa = new JLabel(new ImageIcon(imagem));
        capa.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titulo = new JLabel(livro.getNome(), SwingConstants.CENTER);
        JLabel autorLabel = new JLabel("Autor: " + livro.getAutor(), SwingConstants.CENTER);
        JLabel anoLabel = new JLabel("Ano: " + livro.getAno(), SwingConstants.CENTER);

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.add(titulo);
        info.add(autorLabel);
        info.add(anoLabel);

        card.add(capa, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (admin) {
                    Object[] opcoes = {"Detalhes", "Editar", "Excluir", "Cancelar"};
                    int escolha = JOptionPane.showOptionDialog(
                            BibliotecaFrame.this,
                            livro.getNome(), "Gerir Livro",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, opcoes, opcoes[0]
                    );

                    if (escolha == 0) {
                        mostrarDetalhes(livro);
                    } else if (escolha == 1) {
                        String novoNome = JOptionPane.showInputDialog(BibliotecaFrame.this, "Novo Nome:", livro.getNome());
                        if (novoNome == null) return;
                        
                        String novoAutor = JOptionPane.showInputDialog(BibliotecaFrame.this, "Novo Autor:", livro.getAutor());
                        String novoAno = JOptionPane.showInputDialog(BibliotecaFrame.this, "Novo Ano:", livro.getAno());
                        String novaDescricao = JOptionPane.showInputDialog(BibliotecaFrame.this, "Nova Descrição:", livro.getDescricao());
                        
                        String novoCaminhoImagem = livro.getCaminhoImagem();
                        
                        int mudarFoto = JOptionPane.showConfirmDialog(BibliotecaFrame.this, "Deseja alterar a foto da capa?", "Alterar Foto", JOptionPane.YES_NO_OPTION);
                        
                        if (mudarFoto == JOptionPane.YES_OPTION) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Selecione a nova capa");
                            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
                            if (fileChooser.showOpenDialog(BibliotecaFrame.this) == JFileChooser.APPROVE_OPTION) {
                                novoCaminhoImagem = fileChooser.getSelectedFile().getAbsolutePath();
                            }
                        }
                        
                        bookService.atualizarLivro(livro.getId(), novoNome, novoAutor, novoAno, novaDescricao, novoCaminhoImagem);
                        atualizarPainelLivros();
                        JOptionPane.showMessageDialog(BibliotecaFrame.this, "Livro atualizado com sucesso!");
                    } else if (escolha == 2) {
                        bookService.deletarLivro(livro.getId());
                        atualizarPainelLivros();
                    }
                } else {
                    mostrarDetalhes(livro);
                }
            }
        });

        return card;
    }

    private void mostrarDetalhes(Book livro) {
        JOptionPane.showMessageDialog(
                this,
                "Título: " + livro.getNome() +
                "\nAutor: " + livro.getAutor() +
                "\nAno: " + livro.getAno() +
                "\n\nDescrição:\n" + livro.getDescricao()
        );
    }

    private ImageIcon carregarImagem(String caminho) {
        try {
            var url = getClass().getResource(caminho);
            if (url != null) {
                return new ImageIcon(url);
            } else {
                java.io.File arquivoFisico = new java.io.File(caminho);
                if (arquivoFisico.exists()) {
                    return new ImageIcon(caminho);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + caminho);
        }
        
        return new ImageIcon(new java.awt.image.BufferedImage(140, 190, java.awt.image.BufferedImage.TYPE_INT_RGB));
    }
}