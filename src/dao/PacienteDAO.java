package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  // Adicionada a importação do ResultSet
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mapeamento.Paciente;
import utilitario.Conectar;

public class PacienteDAO {

    public void cadastrar(Paciente p) {
        Connection con = Conectar.getConectar();
        String sql = "INSERT INTO paciente (nome, cpf, email, datanasc, telefone, sexo) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, p.getNome());
            smt.setString(2, p.getCpf());
            smt.setString(3, p.getEmail());
            smt.setString(4, p.getDatanasc());
            smt.setString(5, p.getTelefone());
            smt.setString(6, p.getSexo());
            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    public void atualizar(Paciente p) {
        Connection con = Conectar.getConectar();
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, email = ?, datanasc = ?, telefone = ?, sexo = ? WHERE id_paciente = ?";
        try (PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, p.getNome());
            smt.setString(2, p.getCpf());
            smt.setString(3, p.getEmail());
            smt.setString(4, p.getDatanasc());
            smt.setString(5, p.getTelefone());
            smt.setString(6, p.getSexo());
            smt.setInt(7, p.getId_paciente());
            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro: " + ex.getMessage());
        }
    }

    public void excluir(Paciente p) {
        Connection con = Conectar.getConectar();
        String sql = "DELETE FROM paciente WHERE id_paciente = ?";  // Corrigido para DELETE
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o paciente "
                + p.getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {  // Corrigido para verificar a opção 'YES'
            try (PreparedStatement smt = con.prepareStatement(sql)) {
                smt.setInt(1, p.getId_paciente());
                smt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Excluído com sucesso");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir registro: " + ex.getMessage());
            }
        }
    }

    public List<Paciente> listarTodos() {
        Connection con = Conectar.getConectar();
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente ORDER BY nome";
        try (PreparedStatement smt = con.prepareStatement(sql);
             ResultSet resultado = smt.executeQuery()) {  // Corrigido para usar ResultSet corretamente
            while (resultado.next()) {
                Paciente p = new Paciente();
                p.setId_paciente(resultado.getInt("id_paciente"));
                p.setNome(resultado.getString("nome"));
                p.setCpf(resultado.getString("cpf"));
                p.setEmail(resultado.getString("email"));
                p.setDatanasc(resultado.getString("datanasc"));
                p.setTelefone(resultado.getString("telefone"));
                p.setSexo(resultado.getString("sexo"));
                lista.add(p);
            }
            smt.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os registros: " + ex.getMessage());
        }
        return lista;
    }
}
