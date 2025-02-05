package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mapeamento.Medico;
import utilitario.Conectar;

public class MedicoDAO {

    public void cadastrar(Medico m) {
        String sql = "INSERT INTO medico (nome, email, crm, telefone, especializacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conectar.getConectar(); 
             PreparedStatement smt = con.prepareStatement(sql)) {
            
            smt.setString(1, m.getNome());
            smt.setString(2, m.getEmail());
            smt.setString(3, m.getCrm());
            smt.setString(4, m.getTelefone());
            smt.setString(5, m.getEspecializacao());
            smt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Médico cadastrado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o médico: " + ex.getMessage());
        }
    }

    public void atualizar(Medico m) {
    String sql = "UPDATE medico SET nome = ?, email = ?, crm = ?, telefone = ?, especializacao = ? WHERE id_medico = ?";
    try (Connection con = Conectar.getConectar(); 
         PreparedStatement smt = con.prepareStatement(sql)) {

        smt.setString(1, m.getNome());
        smt.setString(2, m.getEmail());
        smt.setString(3, m.getCrm());
        smt.setString(4, m.getTelefone());
        smt.setString(5, m.getEspecializacao());
        smt.setInt(6, m.getId_medico());

        int rowsAffected = smt.executeUpdate();
        
        // Verificar se a atualização realmente aconteceu
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Médico atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada. Verifique se o ID do médico está correto.");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar o médico: " + ex.getMessage());
    }
}


    public void excluir(Medico m) {
        String sql = "DELETE FROM medico WHERE id_medico = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o médico " + m.getNome() + "?", "Excluir médico", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            try (Connection con = Conectar.getConectar(); 
                 PreparedStatement smt = con.prepareStatement(sql)) {
                
                smt.setInt(1, m.getId_medico());
                smt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Médico excluído com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir o médico: " + ex.getMessage());
            }
        }
    }

    public List<Medico> listarTodos() {
        List<Medico> listaMedico = new ArrayList<>();
        String sql = "SELECT * FROM medico ORDER BY nome";

        try (Connection con = Conectar.getConectar(); 
             PreparedStatement smt = con.prepareStatement(sql); 
             ResultSet resultado = smt.executeQuery()) {

            while (resultado.next()) {
                Medico m = new Medico();
                m.setId_medico(resultado.getInt("id_medico"));
                m.setNome(resultado.getString("nome"));
                m.setEmail(resultado.getString("email"));
                m.setCrm(resultado.getString("crm"));
                m.setTelefone(resultado.getString("telefone"));
                m.setEspecializacao(resultado.getString("especializacao"));
                listaMedico.add(m);
            }
            smt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();  
            JOptionPane.showMessageDialog(null, "Erro ao buscar registros: " + ex.getMessage());
        }
        return listaMedico;
    }
}
