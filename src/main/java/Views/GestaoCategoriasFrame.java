package Views;

import AppService.CategoriaService;
import Infra.Entities.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestaoCategoriasFrame extends JFrame {

    private final CategoriaService categoriaService;
    private DefaultTableModel tableModel;
    private JTable tabela;

    public GestaoCategoriasFrame(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;

        setTitle("Gestão de Categorias");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuração da Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome da Categoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();

        btnAdicionar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome da nova categoria:");
            if (nome != null && !nome.trim().isEmpty()) {
                categoriaService.salvar(nome);
                atualizarTabela();
            }
        });

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria para editar.");
                return;
            }
            Long id = (Long) tableModel.getValueAt(linhaSelecionada, 0);
            String nomeAtual = (String) tableModel.getValueAt(linhaSelecionada, 1);
            
            String novoNome = JOptionPane.showInputDialog(this, "Editar Categoria:", nomeAtual);
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                categoriaService.atualizar(id, novoNome);
                atualizarTabela();
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria para excluir.");
                return;
            }
            Long id = (Long) tableModel.getValueAt(linhaSelecionada, 0);
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                categoriaService.deletar(id);
                atualizarTabela();
            }
        });

        setVisible(true);
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Categoria> categorias = categoriaService.listarTodas();
        for (Categoria cat : categorias) {
            tableModel.addRow(new Object[]{cat.getId(), cat.getNome()});
        }
    }
}