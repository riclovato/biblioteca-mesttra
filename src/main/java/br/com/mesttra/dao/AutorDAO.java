package br.com.mesttra.dao;

import br.com.mesttra.config.DatabaseConnection;
import br.com.mesttra.excecoes.ExcecaoSQL;
import br.com.mesttra.excecoes.ExcecaoNegocio;
import br.com.mesttra.model.AutorModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AutorDAO {

	private final DatabaseConnection gerenciadorBancoDados;

	private final String sqlBase = """
		SELECT	a.id as autor_id ,
					autor as autor_nome,
					pseudonimo as autor_pseudonimo ,
					nacionalidade as autor_nacionalidade,
					endereco_web as autor_endereco_web,
					email as autor_email,
					telefone as autor_telefone
		FROM autor as a
	""";

	public AutorDAO(DatabaseConnection gerenciadorBancoDados) {
		this.gerenciadorBancoDados = gerenciadorBancoDados;
	}
	
	// ATUALIZAR (UPDATE)
	public AutorModel atualizar(int id, AutorModel autor) {
		String sql = """
			UPDATE autor SET 
				autor = ?, 
				pseudonimo = ?, 
				nacionalidade = ?, 
				endereco_web = ?, 
				email = ?, 
				telefone = ? 
			WHERE id = ?
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			comandoSQL.setString(1, autor.getAutor());
			comandoSQL.setString(2, autor.getPseudonimo());
			comandoSQL.setString(3, autor.getNacionalidade());
			comandoSQL.setString(4, autor.getEnderecoWeb());
			comandoSQL.setString(5, autor.getEmail());
			comandoSQL.setString(6, autor.getTelefone());
			comandoSQL.setInt(7, id);

			int linhasAfetadas = comandoSQL.executeUpdate();

			if (linhasAfetadas > 0) {
				autor.setId(id);
				return autor;
			}

         throw new ExcecaoNegocio("Autor com id " + id + " não encontrado");
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao atualizar aluno.", erro);
		}
	}

	// BUSCAR TODOS
	public List<AutorModel> buscarTodos()  {
		List<AutorModel> autores = new ArrayList<>();
		String sql = sqlBase;

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				Statement comandoSQL = conexao.createStatement();
				ResultSet resultado = comandoSQL.executeQuery(sql)) {

			while (resultado.next()) {
				Optional<AutorModel> autor = mapearResultSetParaAutorModel(resultado);

				if (autor.isPresent()) {
					autores.add(autor.get());
				}
			}
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar autores.", erro);
		}

		return autores;
	}

	// BUSCAR POR ID
	public Optional<AutorModel> buscarPorId(int id)  {
		String sql = sqlBase + "WHERE a.id = ?";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			comandoSQL.setInt(1, id);

			try (ResultSet resultado = comandoSQL.executeQuery()) {
				if (resultado.next()) {
					Optional<AutorModel> autorModel = mapearResultSetParaAutorModel(resultado);
					return autorModel;
				}
			}
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar autores.", erro);
		}

		return Optional.empty();
	}
	
	public Optional<List<AutorModel>> buscarPorAutor(String autorParte) {
		List<AutorModel> autores = new ArrayList<>();
		String sql = sqlBase + "WHERE a.autor LIKE ?";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			String termoBusca = "%" + autorParte + "%";
			comandoSQL.setString(1, termoBusca);

			try (ResultSet resultado = comandoSQL.executeQuery()) {
				while (resultado.next()) {
					Optional<AutorModel> autor = mapearResultSetParaAutorModel(resultado);

					if (autor.isPresent()) {
						autores.add(autor.get());
					}
				}
			}
		}  catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar autores.", erro);
		}

		return (autores.isEmpty())? Optional.empty() : Optional.of(autores);
	}
	
	// EXCLUIR (DELETE)
	public boolean deletar(int id)  {
		String sql = """
			DELETE FROM autor WHERE id = ?
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			comandoSQL.setInt(1, id);

			int linhasAfetadas = comandoSQL.executeUpdate();

			return (linhasAfetadas > 0);
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao deletar autor.", erro);
		}	
	}

	// SALVAR (INSERT)
	public AutorModel inserir(AutorModel autor)  {
		String sql = """
			INSERT INTO autor (autor, pseudonimo, nacionalidade, endereco_web, email, telefone) 
			VALUES (?, ?, ?, ?, ?, ?)
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			comandoSQL.setString(1, autor.getAutor());
			comandoSQL.setString(2, autor.getPseudonimo());
			comandoSQL.setString(3, autor.getNacionalidade());
			comandoSQL.setString(4, autor.getEnderecoWeb());
			comandoSQL.setString(5, autor.getEmail());
			comandoSQL.setString(6, autor.getTelefone());

			comandoSQL.executeUpdate();

			try (ResultSet chavesGeradas = comandoSQL.getGeneratedKeys()) {
				if (chavesGeradas.next()) {
					int idGerado = chavesGeradas.getInt(1);
					autor.setId(idGerado);
	
					return autor;
				}
			}
			
			throw new SQLException("Autor inserido, porém não foi possível obter o ID gerado.");

		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao inserir autor.", erro);
		}
	}

	// Mapear ResultSet para AutorModel
	public static Optional<AutorModel> mapearResultSetParaAutorModel(ResultSet resultado)  {
		try {
			int idAutor = resultado.getInt("autor_id");

			if (resultado.wasNull()) {
				// Retorna Optional vazio se o ID do autor for nulo (ou seja, não há autor associado)
				return Optional.empty(); 
			}
			
			AutorModel autor = new AutorModel();
			
			autor.setId(idAutor);
			autor.setAutor(resultado.getString("autor_nome"));
			autor.setPseudonimo(resultado.getString("autor_pseudonimo"));
			autor.setNacionalidade(resultado.getString("autor_nacionalidade"));
			autor.setEnderecoWeb(resultado.getString("autor_endereco_web"));
			autor.setEmail(resultado.getString("autor_email"));
			autor.setTelefone(resultado.getString("autor_telefone"));

			// Retorna o autor mapeado dentro de um Optional
			return Optional.of(autor); 
		}	catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao acessar dados do aluno.", erro);
		}
	}

}
