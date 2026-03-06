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
import br.com.mesttra.model.UsuarioModel;

@Repository
public class UsuarioDAO {
    private final DatabaseConnection gerenciadorBancoDados;

    public UsuarioDAO(DatabaseConnection gerenciadorBancoDados) {
        this.gerenciadorBancoDados = gerenciadorBancoDados;
    }

    public List<UsuarioModel> buscarTodos() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                Statement instrucao = conexao.createStatement();
                ResultSet resultado = instrucao.executeQuery(sql)) {

            while (resultado.next()) {
                UsuarioModel usuario = mapearResultSetParaUsuarioModel(resultado);
                usuarios.add(usuario);
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return usuarios;
    }

    // BUSCAR POR ID
    public UsuarioModel buscarPorId(Integer id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaUsuarioModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR NOME

    public List<UsuarioModel> buscarPorNome(String nomeParte) {
        List<UsuarioModel> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, "%" + nomeParte + "%");
            try (ResultSet resultado = instrucao.executeQuery()) {
                while (resultado.next()) {
                    UsuarioModel usuario = mapearResultSetParaUsuarioModel(resultado);
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return usuarios;
    }

    // SALVAR (INSERT)
    public UsuarioModel salvar(UsuarioModel usuario) {
        String sql = "INSERT INTO usuario (nome, usuario, senha) " +
                "VALUES (?, ?, ?)";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            instrucao.setString(1, usuario.getNome());
            instrucao.setString(2, usuario.getUsuario());
            instrucao.setString(3, usuario.getSenha());

            instrucao.executeUpdate();

            try (ResultSet chavesGeradas = instrucao.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    usuario.setId(chavesGeradas.getInt(1));
                }
            }

            return usuario;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    // ATUALIZAR (UPDATE)
    public UsuarioModel atualizar(Integer id, UsuarioModel usuario) {
        String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ? WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, usuario.getNome());
            instrucao.setString(2, usuario.getUsuario());
            instrucao.setString(3, usuario.getSenha());
            instrucao.setInt(4, id);

            int linhasAfetadas = instrucao.executeUpdate();

            if (linhasAfetadas > 0) {
                usuario.setId(id);
                return usuario;
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

    private UsuarioModel mapearResultSetParaUsuarioModel(ResultSet resultado) throws SQLException {
        return new UsuarioModel(
                resultado.getInt("id"),
                resultado.getString("nome"),
                resultado.getString("usuario"),
                resultado.getString("senha"));
    }
}
