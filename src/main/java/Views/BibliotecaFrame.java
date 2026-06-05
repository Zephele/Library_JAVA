package Views;

import javax.swing.*;
import AppService.UserService;
import AppService.BookService;
import Infra.Entities.Book;

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

    public BibliotecaFrame(boolean admin, UserService userService, BookService bookService) {

        this.admin = admin;
        this.userService = userService;
        this.bookService = bookService; // Injetando o serviço

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

        if (!admin) {
            adicionar.setVisible(false);
        }

        menu.add(logo);
        menu.add(new JLabel());
        menu.add(adicionar);
        menu.add(sair);

        sair.addActionListener(e -> {
            LoginFrame login = new LoginFrame(userService, bookService);
            login.setVisible(true); // Garante que a tela de login vai aparecer na frente
            dispose(); // Destrói a tela da biblioteca
        });

        JPanel topo = new JPanel(new BorderLayout());
        JLabel tituloPrincipal = new JLabel("Biblioteca Digital");
        tituloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        topo.add(tituloPrincipal, BorderLayout.WEST);

        painelLivros = new JPanel();
        painelLivros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // O 0 indica linhas infinitas, 3 colunas
        painelLivros.setLayout(new GridLayout(0, 3, 20, 20)); 

        JScrollPane scrollPane = new JScrollPane(painelLivros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contadorLivros = new JLabel("Total de Livros: 0");

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(scrollPane, BorderLayout.CENTER);
        centro.add(contadorLivros, BorderLayout.SOUTH);

        // Carrega os livros do banco de dados na inicialização
        atualizarPainelLivros();

        // AÇÃO DE ADICIONAR NOVO LIVRO
        adicionar.addActionListener(e -> {

            String nome = JOptionPane.showInputDialog(this, "Nome do Livro:");
            if (nome == null || nome.trim().isEmpty()) return;

            String autor = JOptionPane.showInputDialog(this, "Autor:");
            if (autor == null || autor.trim().isEmpty()) return;

            String ano = JOptionPane.showInputDialog(this, "Ano:");
            if (ano == null || ano.trim().isEmpty()) return;

            String descricao = JOptionPane.showInputDialog(this, "Descrição:");
            if (descricao == null || descricao.trim().isEmpty()) return;

            // NOVO: Abre o explorador de arquivos para escolher a foto
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a capa do livro");
            
            // Filtro para mostrar apenas imagens
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens (*.jpg, *.png)", "jpg", "jpeg", "png"));

            String caminhoImagem = "/imagens/domcasmurro.jpg"; // Imagem padrão caso ele cancele
            
            // Exibe a janela. Se o usuário clicar em "Abrir" (APPROVE_OPTION), pegamos o caminho absoluto
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // Pega o caminho completo do arquivo no PC (ex: C:\Users\Rafael\Imagens\capa.png)
                caminhoImagem = fileChooser.getSelectedFile().getAbsolutePath();
            }

            // Salva no banco de dados com a imagem escolhida!
            bookService.salvarLivro(nome, autor, ano, descricao, caminhoImagem);

            // Recarrega a tela para mostrar o livro novo
            atualizarPainelLivros();
        });
        // Adiciona painéis principais e mostra a janela
        add(menu, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    // MÉTODO QUE FAZ O LAÇO DE REPETIÇÃO (FOR) NO BANCO DE DADOS
    private void atualizarPainelLivros() {
        painelLivros.removeAll(); // Limpa a tela
        
        List<Book> livrosNoBanco = bookService.listarTodos(); // Busca no MySQL
        
        for (Book livro : livrosNoBanco) {
            painelLivros.add(criarCard(livro)); // Cria e adiciona um card para cada livro
        }
        
        painelLivros.revalidate();
        painelLivros.repaint();
        contadorLivros.setText("Total de Livros: " + livrosNoBanco.size());
    }

    // O método criar card agora recebe o Objeto Book
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
                    Object[] opcoes = {"Detalhes", "Excluir", "Cancelar"};
                    int escolha = JOptionPane.showOptionDialog(
                            BibliotecaFrame.this,
                            livro.getNome(), "Gerenciar Livro",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, opcoes, opcoes[0]
                    );

                    if (escolha == 0) { // Detalhes
                        mostrarDetalhes(livro);
                    } else if (escolha == 1) { // Excluir
                        bookService.deletarLivro(livro.getId()); // Exclui do banco
                        atualizarPainelLivros(); // Recarrega a tela
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
            // 1º Tenta carregar da pasta interna resources (para os livros que já vinham no código)
            var url = getClass().getResource(caminho);
            if (url != null) {
                return new ImageIcon(url);
            } else {
                // 2º Tenta carregar direto do disco rígido (para os livros novos adicionados pelo FileChooser)
                java.io.File arquivoFisico = new java.io.File(caminho);
                if (arquivoFisico.exists()) {
                    return new ImageIcon(caminho);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + caminho);
        }
        
        // Retorna um quadrado em branco como placeholder se a imagem não existir
        return new ImageIcon(new java.awt.image.BufferedImage(140, 190, java.awt.image.BufferedImage.TYPE_INT_RGB));
    }
}