package Repository;

import Infra.ConnectionFactory;
import Infra.Entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> listarTodos() {
        List<Book> livros = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book livro = new Book();
                livro.setId(rs.getLong("id"));
                livro.setNome(rs.getString("nome"));
                livro.setAutor(rs.getString("autor"));
                livro.setAno(rs.getString("ano"));
                livro.setDescricao(rs.getString("descricao"));
                livro.setCaminhoImagem(rs.getString("caminho_imagem"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }

    public void salvarLivro(Book livro) {
        String sql = "INSERT INTO Book (nome, autor, ano, descricao, caminho_imagem) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getNome());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getAno());
            stmt.setString(4, livro.getDescricao());
            stmt.setString(5, livro.getCaminhoImagem());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar livro: " + e.getMessage());
        }
    }

    public void atualizarLivro(Long id, String nome, String autor, String ano, String descricao) {
        String sql = "UPDATE Book SET nome = ?, autor = ?, ano = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, autor);
            stmt.setString(3, ano);
            stmt.setString(4, descricao);
            stmt.setLong(5, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    public void deletarLivro(Long id) {
        String sql = "DELETE FROM Book WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
        }
    }

    public long contarLivros() {
        String sql = "SELECT COUNT(*) FROM Book";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar livros: " + e.getMessage());
        }
        return 0;
    }
}