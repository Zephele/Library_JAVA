package Repository;

import Infra.ConnectionFactory;
import Infra.Entities.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setId(rs.getLong("id"));
                cat.setNome(rs.getString("nome"));
                categorias.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }
        return categorias;
    }

    public void salvar(Categoria categoria) {
        String sql = "INSERT INTO Categoria (nome) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar categoria: " + e.getMessage());
        }
    }

    public void atualizar(Long id, String novoNome) {
        String sql = "UPDATE Categoria SET nome = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM Categoria WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar categoria: " + e.getMessage());
        }
    }
}