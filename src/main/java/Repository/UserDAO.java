package Repository;

import Infra.ConnectionFactory;
import Infra.Entities.User;
import java.sql.*;

public class UserDAO {

    public User autenticar(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ? AND is_active = 1";
        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAdmin(rs.getBoolean("is_admin"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar: " + e.getMessage());
        }
        return null;
    }

    public void cadastrar(User user) {
        String sql = "INSERT INTO User (username, password, name, is_active, is_admin) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setBoolean(4, user.isActive());
            stmt.setBoolean(5, user.isAdmin());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public long contarUsuarios() {
        String sql = "SELECT COUNT(*) FROM User";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar utilizadores: " + e.getMessage());
        }
        return 0;
    }
}