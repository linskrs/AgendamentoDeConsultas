package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mapeamento.Funcionario;
import utilitario.Conectar;

public class FuncionarioDAO {

    public void cadastrar(Funcionario r) {
        String sql = "INSERT INTO funcionario (nome, cpf, email, telefone, dataadmissao, senha) VALUES(?, ?, ?, ?, ?, MD5(?))";

        try (Connection con = Conectar.getConectar(); PreparedStatement smt = con.prepareStatement(sql)) {

            smt.setString(1, r.getNome());
            smt.setString(2, r.getCpf());
            smt.setString(3, r.getEmail());
            smt.setString(4, r.getTelefone());
            smt.setString(5, r.getDataadmissao());
            smt.setString(6, r.getSenha());

            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar funcionário: " + ex.getMessage());
        }
    }

    public void atualizar(Funcionario r) {
        String sql = "UPDATE funcionario SET nome = ?, cpf = ?, email = ?, telefone = ?, dataadmissao = ? WHERE id_funcionario = ?";

        try (Connection con = Conectar.getConectar(); PreparedStatement smt = con.prepareStatement(sql)) {

            smt.setString(1, r.getNome());
            smt.setString(2, r.getCpf());
            smt.setString(3, r.getEmail());
            smt.setString(4, r.getTelefone());
            smt.setString(5, r.getDataadmissao());
            smt.setInt(6, r.getId_funcionario());

            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionário: " + ex.getMessage());
        }
    }

    public void excluir(Funcionario r) {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o funcionário " + r.getNome() + "?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            try (Connection con = Conectar.getConectar(); PreparedStatement smt = con.prepareStatement(sql)) {
                smt.setInt(1, r.getId_funcionario());
                smt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir funcionário: " + ex.getMessage());
            }
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> listaFuncionario = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome";

        try (Connection con = Conectar.getConectar(); PreparedStatement smt = con.prepareStatement(sql); ResultSet resultado = smt.executeQuery()) {

            while (resultado.next()) {
                Funcionario f = new Funcionario();
                f.setId_funcionario(resultado.getInt("id_funcionario"));
                f.setNome(resultado.getString("nome"));
                f.setCpf(resultado.getString("cpf"));
                f.setEmail(resultado.getString("email"));
                f.setTelefone(resultado.getString("telefone"));
                f.setDataadmissao(resultado.getString("dataadmissao"));
                listaFuncionario.add(f);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os funcionários: " + ex.getMessage());
        }

        return listaFuncionario;
    }

    public Funcionario login(String cpf, String senha) {
        Connection con = null;
        PreparedStatement smt = null;
        ResultSet resultado = null;
        Funcionario f = null;

        String sql = "SELECT * FROM funcionario WHERE cpf = ? AND senha = MD5(?)";

        try {
            con = Conectar.getConectar();
            smt = con.prepareStatement(sql);
            smt.setString(1, cpf);
            smt.setString(2, senha);
            resultado = smt.executeQuery();

            if (resultado.next()) {
                f = new Funcionario();
                f.setId_funcionario(resultado.getInt("id_funcionario"));
                f.setNome(resultado.getString("nome"));
                f.setCpf(resultado.getString("cpf"));
                f.setEmail(resultado.getString("email"));
                f.setTelefone(resultado.getString("telefone"));
                f.setDataadmissao(resultado.getString("dataadmissao"));
                f.setSenha(resultado.getString("senha"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao autenticar: " + ex.getMessage());
        } finally {
            try {
                if (resultado != null) resultado.close();
                if (smt != null) smt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + ex.getMessage());
            }
        }

        return f;
    }

    public static void main(String[] args) {
        // Exemplo de uso para teste
        FuncionarioDAO dao = new FuncionarioDAO();
        
        // Testar listagem de funcionários
        List<Funcionario> funcionarios = dao.listarTodos();
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome());
        }
    }
}