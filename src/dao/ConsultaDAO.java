package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import mapeamento.Consulta;
import utilitario.Conectar;

public class ConsultaDAO {

    public void cadastrar(Consulta c) {
        Connection con = Conectar.getConectar();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO consulta (dataAtendimento, horario, id_paciente, id_medico, id_funcionario) VALUES (?, ?, ?, ?, ?)";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, c.getData());
            stmt.setString(2, c.getHoras());
            stmt.setInt(3, c.getPaciente().getId_paciente());
            stmt.setInt(4, c.getMedico().getId_medico());
            stmt.setInt(5, c.getFuncionario().getId_funcionario());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao agendar consulta: " + e.getMessage());
        } finally {
            Conectar.closeConnection(con, stmt);
        }
    }
}
