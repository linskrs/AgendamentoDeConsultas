package utilitario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author linik
 */
public class Conectar {
    private static final String usuario = "root";
    private static final String senha = "3536";
    private static final String url = "jdbc:mysql://localhost/consultorio";
    
    // Método para abrir a conexão
    public static Connection getConectar(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Atualizado para a versão mais recente do driver
            con = DriverManager.getConnection(url, usuario, senha);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar no banco de dados: " + ex.getMessage());
        }
        return con;
    }

    // Método para fechar apenas a conexão
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    // Método para fechar conexão + PreparedStatement
    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
        }
    }

    // Método para fechar conexão + PreparedStatement + ResultSet
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
        }
    }
}
