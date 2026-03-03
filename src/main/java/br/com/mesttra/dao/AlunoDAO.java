package br.com.mesttra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.mesttra.config.DatabaseConnection;
import br.com.mesttra.model.AlunoModel;

@Repository
public class AlunoDAO {

    private final DatabaseConnection gerenciadorBancoDados;

    public AlunoDAO(DatabaseConnection gerenciadorBancoDados) {
        this.gerenciadorBancoDados = gerenciadorBancoDados;
    }

    // BUSCAR TODOS
    public List<AlunoModel> buscarTodos() {
        List<AlunoModel> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             Statement instrucao = conexao.createStatement();
             ResultSet resultado = instrucao.executeQuery(sql)) {

            while (resultado.next()) {
                AlunoModel aluno = mapearResultSetParaAlunoModel(resultado);
                alunos.add(aluno);
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return alunos;
    }

    // BUSCAR POR ID
    public AlunoModel buscarPorId(Integer id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaAlunoModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR NOME
    public AlunoModel buscarPorNome(String nome) {
        String sql = "SELECT * FROM aluno WHERE nome like ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, "%" + nome + "%");
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaAlunoModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // SALVAR (INSERT)
    public AlunoModel salvar(AlunoModel aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, telefone, email, cep, estado, cidade, endereco, bairro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             PreparedStatement instrucao = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            instrucao.setString(1, aluno.getNome());
            instrucao.setString(2, aluno.getCpf());
            instrucao.setString(3, aluno.getTelefone());
            instrucao.setString(4, aluno.getEmail());
            instrucao.setString(5, aluno.getCep());
            instrucao.setString(6, aluno.getEstado());
            instrucao.setString(7, aluno.getCidade());
            instrucao.setString(8, aluno.getEndereco());
            instrucao.setString(9, aluno.getBairro());

            instrucao.executeUpdate();

            try (ResultSet chavesGeradas = instrucao.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    aluno.setId(chavesGeradas.getInt(1));
                }
            }

            return aluno;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    // ATUALIZAR (UPDATE)
    public AlunoModel atualizar(Integer id, AlunoModel aluno) {
        String sql = "UPDATE aluno SET nome = ?, cpf = ?, telefone = ?, email = ?, cep = ?, " +
                     "estado = ?, cidade = ?, endereco = ?, bairro = ? WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, aluno.getNome());
            instrucao.setString(2, aluno.getCpf());
            instrucao.setString(3, aluno.getTelefone());
            instrucao.setString(4, aluno.getEmail());
            instrucao.setString(5, aluno.getCep());
            instrucao.setString(6, aluno.getEstado());
            instrucao.setString(7, aluno.getCidade());
            instrucao.setString(8, aluno.getEndereco());
            instrucao.setString(9, aluno.getBairro());
            instrucao.setInt(10, id);

            int linhasAfetadas = instrucao.executeUpdate();

            if (linhasAfetadas > 0) {
                aluno.setId(id);
                return aluno;
            }

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // DELETAR (DELETE)
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
             PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            int linhasAfetadas = instrucao.executeUpdate();

            return linhasAfetadas > 0;

			} catch (SQLException erro) {
			  throw new RuntimeException(erro);
        }

    }

    // Método auxiliar para mapear ResultSet para AlunoModel
    private AlunoModel mapearResultSetParaAlunoModel(ResultSet resultado) throws SQLException {
        return new AlunoModel(
                resultado.getInt("id"),
                resultado.getString("nome"),
                resultado.getString("cpf"),
                resultado.getString("telefone"),
                resultado.getString("email"),
                resultado.getString("cep"),
                resultado.getString("estado"),
                resultado.getString("cidade"),
                resultado.getString("endereco"),
                resultado.getString("bairro")
        );
    }
}
