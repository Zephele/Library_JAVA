package Views;

import javax.swing.*;

import AppService.UserService;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BibliotecaFrame extends JFrame {

    private final UserService userService;
    private JPanel painelLivros;
    private JLabel contadorLivros;
    private boolean admin;

    public BibliotecaFrame(boolean admin, UserService userService) {

        this.admin = admin;
        this.userService = userService;

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

        JPanel topo = new JPanel(new BorderLayout());

        JLabel tituloPrincipal = new JLabel("Biblioteca Digital");
        tituloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 30));

        topo.add(tituloPrincipal, BorderLayout.WEST);

        painelLivros = new JPanel();
        painelLivros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelLivros.setLayout(new GridLayout(2, 3, 20, 20));

        // Adicionar livros padrão
        painelLivros.add(
                criarCard(
                        "Dom Casmurro",
                        "Machado de Assis",
                        "1899",
                        "Romance clássico brasileiro que narra a história de Bentinho e Capitu.",
                        "/imagens/domcasmurro.jpg"
                )
        );

        painelLivros.add(
                criarCard(
                        "1984",
                        "George Orwell",
                        "1949",
                        "Uma distopia sobre um governo totalitário que vigia toda a população.",
                        "/imagens/1984.jpg"
                )
        );

        painelLivros.add(
                criarCard(
                        "O Pequeno Príncipe",
                        "Antoine de Saint-Exupéry",
                        "1943",
                        "Uma obra sobre amizade, amor e o verdadeiro valor das coisas.",
                        "/imagens/opequenoprincipe.jpg"
                )
        );

        // Envolver em JScrollPane para suportar muitos livros
        JScrollPane scrollPane = new JScrollPane(painelLivros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contadorLivros = new JLabel("Total de Livros: " + painelLivros.getComponentCount());

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(topo, BorderLayout.NORTH);
        centro.add(scrollPane, BorderLayout.CENTER);
        centro.add(contadorLivros, BorderLayout.SOUTH);

        // Adicionar novo livro
        adicionar.addActionListener(e -> {

            String nome = JOptionPane.showInputDialog(this, "Nome do Livro:");

            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome do livro é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String autor = JOptionPane.showInputDialog(this, "Autor:");
            if (autor == null || autor.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Autor é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String ano = JOptionPane.showInputDialog(this, "Ano:");
            if (ano == null || ano.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ano é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descricao = JOptionPane.showInputDialog(this, "Descrição:");
            if (descricao == null || descricao.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Descrição é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            painelLivros.add(
                    criarCard(
                            nome,
                            autor,
                            ano,
                            descricao,
                            "/imagens/domcasmurro.jpg"
                    )
            );

            painelLivros.revalidate();
            painelLivros.repaint();
            atualizarContador();
        });

        // Sair
        sair.addActionListener(e -> {
            new LoginFrame(userService);
            dispose();
        });

        add(menu, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel criarCard(
            String nome,
            String autor,
            String ano,
            String descricao,
            String caminhoImagem
    ) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Carregar imagem do classpath com fallback
        ImageIcon icon = carregarImagem(caminhoImagem);

        Image imagem = icon.getImage().getScaledInstance(140, 190, Image.SCALE_SMOOTH);

        JLabel capa = new JLabel(new ImageIcon(imagem));
        capa.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titulo = new JLabel(nome, SwingConstants.CENTER);
        JLabel autorLabel = new JLabel("Autor: " + autor, SwingConstants.CENTER);
        JLabel anoLabel = new JLabel("Ano: " + ano, SwingConstants.CENTER);

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.add(titulo);
        info.add(autorLabel);
        info.add(anoLabel);

        card.add(capa, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);

        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        card.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (admin) {

                            Object[] opcoes = {"Detalhes", "Excluir", "Cancelar"};

                            int escolha = JOptionPane.showOptionDialog(
                                    BibliotecaFrame.this,
                                    nome,
                                    "Livro",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    opcoes,
                                    opcoes[0]
                            );

                            if (escolha == 0) {
                                JOptionPane.showMessageDialog(
                                        BibliotecaFrame.this,
                                        "Título: " + nome +
                                                "\nAutor: " + autor +
                                                "\nAno: " + ano +
                                                "\n\nDescrição:\n" +
                                                descricao
                                );
                            } else if (escolha == 1) {
                                painelLivros.remove(card);
                                painelLivros.revalidate();
                                painelLivros.repaint();
                                atualizarContador();
                            }

                        } else {
                                JOptionPane.showMessageDialog(
                                BibliotecaFrame.this,
                                "Título: " + nome +
                                "\nAutor: " + autor +
                                "\nAno: " + ano +
                                "\n\nDescrição:\n" +
                                descricao
                        );
                        }
                }
                }
        );

        return card;
    }

    private ImageIcon carregarImagem(String caminho) {
        try {
            var url = getClass().getResource(caminho);
            if (url != null) {
                return new ImageIcon(url);
            }
        } catch (Exception e) {
            System.err.println("Imagem não encontrada: " + caminho);
        }
        // Retorna placeholder se imagem não existir
        return new ImageIcon(new java.awt.image.BufferedImage(140, 190, java.awt.image.BufferedImage.TYPE_INT_RGB));
    }

    private void atualizarContador() {
        contadorLivros.setText("Total de Livros: " + painelLivros.getComponentCount());
    }
}