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
import br.com.mesttra.model.CategoriaModel;

@Repository
public class CategoriaDAO {

    private final DatabaseConnection gerenciadorBancoDados;

    public CategoriaDAO(DatabaseConnection gerenciadorBancoDados) {
        this.gerenciadorBancoDados = gerenciadorBancoDados;
    }

    public List<CategoriaModel> buscarTodos() {
        List<CategoriaModel> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                Statement instrucao = conexao.createStatement();
                ResultSet resultado = instrucao.executeQuery(sql)) {

            while (resultado.next()) {
                CategoriaModel categoria = mapearResultSetParaCategoriaModel(resultado);
                categorias.add(categoria);
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return categorias;
    }

    // BUSCAR POR ID
    public CategoriaModel buscarPorId(Integer id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setInt(1, id);
            try (ResultSet resultado = instrucao.executeQuery()) {
                if (resultado.next()) {
                    return mapearResultSetParaCategoriaModel(resultado);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return null;
    }

    // BUSCAR POR NOME

    public List<CategoriaModel> buscarPorNome(String nomeParte) {
        List<CategoriaModel> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE categoria LIKE ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, "%" + nomeParte + "%");
            try (ResultSet resultado = instrucao.executeQuery()) {
                while (resultado.next()) {
                    CategoriaModel categoria = mapearResultSetParaCategoriaModel(resultado);
                    categorias.add(categoria);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return categorias;
    }

    // SALVAR (INSERT)
    public CategoriaModel salvar(CategoriaModel categoria) {
        String sql = "INSERT INTO categoria (categoria, descricao) " +
                "VALUES (?, ?)";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            instrucao.setString(1, categoria.getCategoria());
            instrucao.setString(2, categoria.getDescricao());

            instrucao.executeUpdate();

            try (ResultSet chavesGeradas = instrucao.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    categoria.setId(chavesGeradas.getInt(1));
                }
            }

            return categoria;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    // ATUALIZAR (UPDATE)
    public CategoriaModel atualizar(Integer id, CategoriaModel categoria) {
        String sql = "UPDATE categoria SET categoria = ?, descricao = ? WHERE id = ?";

        try (Connection conexao = gerenciadorBancoDados.obterConexao();
                PreparedStatement instrucao = conexao.prepareStatement(sql)) {

            instrucao.setString(1, categoria.getCategoria());
            instrucao.setString(2, categoria.getDescricao());
            instrucao.setInt(3, id);
            instrucao.setInt(7, id);

            int linhasAfetadas = instrucao.executeUpdate();

            if (linhasAfetadas > 0) {
                categoria.setId(id);
                return categoria;
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

    private CategoriaModel mapearResultSetParaCategoriaModel(ResultSet resultado) throws SQLException {
        return new CategoriaModel(
                resultado.getInt("id"),
                resultado.getString("categoria"),
                resultado.getString("descricao"));
    }
}
