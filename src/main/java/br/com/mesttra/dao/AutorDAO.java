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
import br.com.mesttra.model.AutorModel;

@Repository
public class AutorDAO {
    private final DatabaseConnection gerenciadorBancoDados;

    public AutorDAO(DatabaseConnection gerenciadorBancoDados) {
        this.gerenciadorBancoDados = gerenciadorBancoDados;
    }

    public List<AutorModel> buscarTodos() {
        List<AutorModel> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                Statement instrucao = conexao.createStatement();
                ResultSet resultado = instrucao.executeQuery(sql)) {

            while (resultado.next()) {
                AutorModel autor = mapearResultSetParaAutorModel(resultado);
                autores.add(autor);
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return autores;
    }

    // BUSCAR POR ID
    public AutorModel buscarPorId(Integer id) {
        String sql = "SELECT * FROM autor WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaAutorModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR NOME
    public AutorModel buscarPorNome(String nome) {
        String sql = "SELECT * FROM autor WHERE autor like ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, "%" + nome + "%");
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaAutorModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // SALVAR (INSERT)
    public AutorModel salvar(AutorModel autor) {
        String sql = "INSERT INTO autor (autor, pseudonimo, nacionalidade, endereco_web, email, telefone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            instrucao.setString(1, autor.getAutor());
            instrucao.setString(2, autor.getPseudonimo());
            instrucao.setString(3, autor.getNacionalidade());
            instrucao.setString(4, autor.getEnderecoWeb());
            instrucao.setString(5, autor.getEmail());
            instrucao.setString(6, autor.getTelefone());

            instrucao.executeUpdate();

            try (ResultSet chavesGeradas = instrucao.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    autor.setId(chavesGeradas.getInt(1));
                }
            }

            return autor;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    // ATUALIZAR (UPDATE)
    public AutorModel atualizar(Integer id, AutorModel autor) {
        String sql = "UPDATE autor SET autor = ?, pseudonimo = ?, nacionalidade = ?, endereco_web = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, autor.getAutor());
            instrucao.setString(2, autor.getPseudonimo());
            instrucao.setString(3, autor.getNacionalidade());
            instrucao.setString(4, autor.getEnderecoWeb());
            instrucao.setString(5, autor.getEmail());
            instrucao.setString(6, autor.getTelefone());
            instrucao.setInt(7, id);

            int linhasAfetadas = instrucao.executeUpdate();

            if (linhasAfetadas > 0) {
                autor.setId(id);
                return autor;
            }

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // DELETAR (DELETE)
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM autor WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            int linhasAfetadas = instrucao.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    private AutorModel mapearResultSetParaAutorModel(ResultSet resultado) throws SQLException {
        return new AutorModel(
                resultado.getInt("id"),
                resultado.getString("autor"),
                resultado.getString("pseudonimo"),
                resultado.getString("nacionalidade"),
                resultado.getString("endereco_web"),
                resultado.getString("email"),
                resultado.getString("telefone"));
    }
}
