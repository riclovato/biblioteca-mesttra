package br.com.mesttra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import br.com.mesttra.config.DatabaseConnection;
import br.com.mesttra.model.EmprestimoModel;

@Repository
public class EmprestimoDAO {

    private final DatabaseConnection gerenciadorBancoDados;

    public EmprestimoDAO(DatabaseConnection gerenciadorBancoDados) {
        this.gerenciadorBancoDados = gerenciadorBancoDados;
    }

    // BUSCAR TODOS
    public List<EmprestimoModel> buscarTodos() {
        List<EmprestimoModel> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                Statement instrucao = conexao.createStatement();
                ResultSet resultado = instrucao.executeQuery(sql)) {

            while (resultado.next()) {
                EmprestimoModel emprestimo = mapearResultSetParaEmprestimoModel(resultado);
                emprestimos.add(emprestimo);
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return emprestimos;
    }

    // BUSCAR POR ID
    public EmprestimoModel buscarPorId(Integer id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaEmprestimoModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR NOME
    public List<EmprestimoModel> buscarPorNome(String nomeParte) {
        List<EmprestimoModel> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE nome LIKE ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, "%" + nomeParte + "%");
            try (ResultSet resultado = instrucao.executeQuery()) {
                while (resultado.next()) {
                    EmprestimoModel emprestimo = mapearResultSetParaEmprestimoModel(resultado);
                    emprestimos.add(emprestimo);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return emprestimos;
    }

    // SALVAR (INSERT)
    public EmprestimoModel salvar(EmprestimoModel emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (id_usuario, id_aluno, data_emprestimo, data_devolucao_prevista) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            instrucao.setInt(1, emprestimo.getIdUsuario());
            instrucao.setInt(2, emprestimo.getIdAluno());
            instrucao.setDate(3, java.sql.Date.valueOf(emprestimo.getDataEmprestimo()));
            instrucao.setDate(4, java.sql.Date.valueOf(emprestimo.getDataDevolucaoPrevista()));

            instrucao.executeUpdate();

            try (ResultSet chavesGeradas = instrucao.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    emprestimo.setId(chavesGeradas.getInt(1));
                }
            }

            return emprestimo;
        }
    }

    // ATUALIZAR (UPDATE)
    public EmprestimoModel atualizar(Integer id, EmprestimoModel emprestimo) {
        String sql = "UPDATE emprestimo SET id_usuario = ?, id_aluno = ?, data_emprestimo = ?, data_devolucao_prevista = ? WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, emprestimo.getIdUsuario());
            instrucao.setInt(2, emprestimo.getIdAluno());
            instrucao.setDate(3, java.sql.Date.valueOf(emprestimo.getDataEmprestimo()));
            instrucao.setDate(4, java.sql.Date.valueOf(emprestimo.getDataDevolucaoPrevista()));

            int linhasAfetadas = instrucao.executeUpdate();

            if (linhasAfetadas > 0) {
                emprestimo.setId(id);
                return emprestimo;
            }

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // DELETAR (DELETE)
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            int linhasAfetadas = instrucao.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Método auxiliar para mapear ResultSet para EmprestimoModel
    private EmprestimoModel mapearResultSetParaEmprestimoModel(ResultSet resultado) throws SQLException {
        return new EmprestimoModel(
                resultado.getInt("id"),
                resultado.getInt("id_usuario"),
                resultado.getInt("id_aluno"),
                resultado.getDate("data_emprestimo").toLocalDate(),
                resultado.getDate("data_devolucao_prevista").toLocalDate()
        );
    }
}
