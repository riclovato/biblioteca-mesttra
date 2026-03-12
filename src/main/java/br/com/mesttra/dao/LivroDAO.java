package br.com.mesttra.dao;

import br.com.mesttra.config.DatabaseConnection;
import br.com.mesttra.dto.LivroAdicionarRemoverAutoresDTO;
import br.com.mesttra.dto.LivroAtualizarTodosAutoresDTO;
import br.com.mesttra.dto.LivroSalvarDTO;
import br.com.mesttra.model.AutorModel;
import br.com.mesttra.model.CategoriaModel;
import br.com.mesttra.model.EditoraModel;
import br.com.mesttra.model.LivroModel;

import br.com.mesttra.excecoes.ExcecaoNegocio;
import br.com.mesttra.excecoes.ExcecaoSQL;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LivroDAO {
	
	private final DatabaseConnection gerenciadorBancoDados;
	// Consulta SQL para buscar livros com seus autores, categoria e editora
	// utilizando joins. Ajustamos os nomes das colunas com aliases (id, nome ...) 
	// para evitar conflitos de nomes entre as tabelas e
	// facilitar o mapeamento dos dados para os objetos Java
	private final String sqlStr = """
		SELECT 	l.id as livro_id, l.titulo as livro_titulo, l.ano_publicacao as livro_ano_publicacao, l.isbn as livro_isbn,
					c.id as categoria_id, c.categoria as categoria_nome, c.descricao as categoria_descricao,
					e.id as editora_id, e.editora as editora_nome, e.cnpj as editora_cnpj, e.email as editora_email,
					e.telefone as editora_telefone, e.cep as editora_cep, e.estado as editora_estado,
					e.cidade as editora_cidade, e.bairro as editora_bairro, e.endereco as editora_endereco,
					e.nacionalidade as editora_nacionalidade, e.endereco_web as editora_endereco_web,
					a.id as autor_id, a.autor as autor_nome, a.pseudonimo as autor_pseudonimo, a.nacionalidade as autor_nacionalidade,
					a.endereco_web as autor_endereco_web, a.email as autor_email, a.telefone as autor_telefone
		FROM livro as l
		LEFT JOIN categoria c on l.id_categoria = c.id
		LEFT JOIN editora e on l.id_editora = e.id
		LEFT JOIN livro_autor al on l.id = al.id_livro
		LEFT JOIN autor a on al.id_autor = a.id """;
		
	public LivroDAO(DatabaseConnection gerenciadorBancoDados) {
		this.gerenciadorBancoDados = gerenciadorBancoDados;
	}
		
	// ATUALIZAR (UPDATE)
	public LivroSalvarDTO atualizar(int id, LivroSalvarDTO livro)  {
		String sql = """
			UPDATE livro SET 
				id_categoria = ?, 
				id_editora = ?, 
				titulo = ?, 
				ano_publicacao = ?, 
				isbn = ? 
			WHERE id = ?
		""";;
		
		try (Connection conexao = gerenciadorBancoDados.obterConexao();
			PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
			
			comandoSQL.setInt(1, livro.getCategoriaId());
			comandoSQL.setInt(2, livro.getEditoraId());
			comandoSQL.setString(3, livro.getTitulo());
			comandoSQL.setInt(4, livro.getAnoPublicacao());
			comandoSQL.setString(5, livro.getIsbn());
			comandoSQL.setInt(6, id);
			
			System.out.println(comandoSQL.toString());
			int linhasAfetadas = comandoSQL.executeUpdate();
		
			if (linhasAfetadas > 0) {
				livro.setId(id);

				return livro;
			}

         throw new ExcecaoNegocio("Livro com id " + id + " não encontrado");
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao atualizar livro.", erro);
		}
	}	 

	// BUSCAR TODOS
	public List<LivroModel> buscarTodos() {
		String sql = sqlStr + " ORDER BY livro_titulo";
		
		List<LivroModel> livros = new ArrayList<>();
		
		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				Statement comandoSQL = conexao.createStatement();
				ResultSet resultado = comandoSQL.executeQuery(sql)) {

			livros = processarResultSet(resultado);

		} 	catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar livros.", erro);
		}

		return livros;
	}
	
	// BUSCAR POR ID
	public Optional<Object> buscarPorId(int id)  {
		String sql = sqlStr + " WHERE l.id = ? ORDER BY livro_titulo";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			comandoSQL.setInt(1, id);

			try (ResultSet resultado = comandoSQL.executeQuery()) {
				// como o processarResultSet retorna uma lista e a busca por id deve retornar
				// apenas um livro, então pegamos o primeiro elemento da lista retornada
				List<LivroModel> livros = processarResultSet(resultado);

				if (livros.size() > 0) {
					return Optional.of(livros.get(0));
				}

				return Optional.empty();

			}
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar alunos.", erro);
		}
	}

	public Optional<List<LivroModel>> buscarPorTitulo(String tituloParte)  {
		List<LivroModel> livros = new ArrayList<>();
	
		String sql = sqlStr + " WHERE l.titulo LIKE ? ORDER BY livro_titulo";
	
		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
	
			String termoBusca = "%" + tituloParte + "%";
			comandoSQL.setString(1, termoBusca);
	
			try (ResultSet resultado = comandoSQL.executeQuery()) {
				List<LivroModel> livro = processarResultSet(resultado);
				livros.addAll(livro);
			}
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao buscar livros.", erro);
		}
	
		return (livros.isEmpty())? Optional.empty() : Optional.of(livros);
	}

	private List<LivroModel> processarResultSet(ResultSet resultado) {
		// A selecao dos dados de livros com seus autores, categoria e editora
		// utilizando joins pode resultar em multiplas linhas para o mesmo livro,
		// caso o livro tenha mais de um autor, por exemplo.Quando isso acontece,
		// o livro é repetido em cada linha do resultado, mas os dados do livro são
		// os mesmos, apenas os dados dos autores são diferentes. Não podemos
		// simplesmente criar um novo objeto LivroModel para cada linha do resultado,
		// pois isso resultaria em livros duplicados na lista de livros retornada.
		// Para resolver esse problema, utilizamos o HashMap como memoria temporaria
		// para armazenar os livros já criados, utilizando o id do livro como chave.

		// algoritmo em portugues:
		// 	Criar um mapa vazio para armazenar os livros já processados, utilizando o id
		// 	do livro como chave
		// 		Para cada linha do resultado da consulta
		// 			Obtem o id do livro encontrado no ResultSet
		// 			Obtem o livro encontrado no HashMap, caso o livro ainda não tenha sido
		// 			encontrado, o valor retornado será null
		// 				se o livro ainda não foi criado
		// 					cria o livro e adiciona os dados do livro encontrado no ResultSet
		// 					constroi o objeto categoria com os dados da consulta
		// 					constroi o objeto editora com os dados da consulta
		//  					constroi o objeto autor com os dados da consulta
		// 					adiciona o livro ao mapa de livros
		// 			adiciona autor ao livro
		//			busca a proxima linha
		//		Retorna a lista de livros processados
		Map<Integer, LivroModel> livrosJaProcessados = new LinkedHashMap<>();

		try {
			while (resultado.next()) {

				// obtem o id do livro encontrado no ResultSet
				int idLivro = resultado.getInt("livro_id");

				// busca o id do livro na memoria/mapa (livrosJaProcessados)  
				// se o livro nao existir no maoa cria um novo objeto livro
				LivroModel livroModel = livrosJaProcessados.get(idLivro);

				// se o livro ainda não foi criado
				if (livroModel == null) {

					// cria o livro e adiciona os dados do livro encontrado no ResultSet
					livroModel = mapearResultSetParaLivroModel(resultado);

					// constroi o objeto categoria com os dados da consulta
					CategoriaModel categoriaModel = CategoriaDAO.mapearResultSetParaCategoriaModel(resultado);
					livroModel.setCategoria(categoriaModel);

					// constroi o objeto editora com os dados da consulta
					EditoraModel editora = EditoraDAO.mapearResultSetParaEditoraModel(resultado);
					livroModel.setEditora(editora);

					// adiciona livro ao HashMap, utilizando o id do livro como chave, para que seja
					// possível encontrar o livro já processado posteriormente quando encontrar uma nova
					// linha que faz referencia a este livro. Podemos simplesmente adicionar os demais 
					// autores do livro ao encotrar novas linhas no resultset da consulta
					livrosJaProcessados.put(idLivro, livroModel);
				}

				// constroi o objeto autor com os dados da consulta
				Optional<AutorModel> autor = AutorDAO.mapearResultSetParaAutorModel(resultado);

				// adiciona autor ao livro
				if (autor.isPresent()) {
					livroModel.setAutor(autor.get());
				}
			}
		} catch (Exception erro) {
			erro.printStackTrace();
		}

		return new ArrayList<>(livrosJaProcessados.values());
	}
	
	public boolean excluirUmAutor(int idLivro, int idAutor) {
		String sqlDelete = """
			DELETE FROM livro_autor WHERE id_livro = ? AND id_autor = ?
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQLDelete = conexao.prepareStatement(sqlDelete);) {

			// Deleta um autor especifico do livro
			comandoSQLDelete.setInt(1, idLivro);
			comandoSQLDelete.setInt(2, idAutor);

			// identifica a quantidade de linhas afetadas
			int linhasAfetadas = comandoSQLDelete.executeUpdate();

			return (linhasAfetadas > 0);
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao deletar aluno.", erro);
		}	
	}
	
	public boolean adicionarRemoverAutores(int idLivro, LivroAdicionarRemoverAutoresDTO parametros)  {
		String sqlInsert = """
			INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)
		""";
		
		String sqlDelete = """
			DELETE FROM livro_autor WHERE id_livro = ? and id_autor = ?
		""";


		try (Connection conexao = gerenciadorBancoDados.obterConexao();){

			conexao.setAutoCommit(false);

			try (PreparedStatement comandoSQLInsert = conexao.prepareStatement(sqlInsert);
					PreparedStatement comandoSQLDelete = conexao.prepareStatement(sqlDelete)) {

				// Insere os novos autores
				for (int autorId : parametros.getIdsAutoresAdicionar()) {
					comandoSQLInsert.setInt(1, idLivro);
					comandoSQLInsert.setInt(2, autorId);
					comandoSQLInsert.addBatch();
				}

				// Deleta os autores removidos
				for (int autorId : parametros.getIdsAutoresRemover()) {
					comandoSQLDelete.setInt(1, idLivro);
					comandoSQLDelete.setInt(2, autorId);
					comandoSQLDelete.addBatch();
				}

				comandoSQLInsert.executeBatch();

				conexao.commit();

				return true;

			} catch (SQLException erro) {
				conexao.rollback();
				throw new ExcecaoSQL("Erro ao atualizar os autores.", erro);
			} 
		}  catch (Exception e) {
			throw new ExcecaoSQL("Erro ao obter conexão com o banco de dados.", e);
		}
	}


	public boolean atualizarTodosAutores(int idLivro, LivroAtualizarTodosAutoresDTO livroAtualizarTodosAutoresDTO) {
		String sqlDelete = """
			DELETE FROM livro_autor WHERE id_livro = ?
		""";

		String sqlInsert = """
			INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao()){

			conexao.setAutoCommit(false);

			try (	PreparedStatement comandoSQLDelete = conexao.prepareStatement(sqlDelete);
					PreparedStatement comandoSQLInsert = conexao.prepareStatement(sqlInsert)) {

				// Deleta os autores atuais
				comandoSQLDelete.setInt(1, idLivro);
				comandoSQLDelete.executeUpdate();

				// Insere os novos autores
				for (int autorId : livroAtualizarTodosAutoresDTO.getIdsAutores()) {
					comandoSQLInsert.setInt(1, idLivro);
					comandoSQLInsert.setInt(2, autorId);
					comandoSQLInsert.addBatch();
				}
				
				comandoSQLInsert.executeBatch();

				conexao.commit();

				return true;
			} catch (SQLException erro) {
				conexao.rollback();
				throw new ExcecaoSQL("Erro ao atualizar os autores.", erro);
			}

		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao realizar o rollback.", erro);
		} 

	}

	// EXCLUIR (DELETE)
	public boolean remover(int id) {
		String sql = """
			DELETE FROM livro WHERE id = ?
		""";

		try (	Connection conexao = gerenciadorBancoDados.obterConexao();
				PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

			comandoSQL.setInt(1, id);

			int linhasAfetadas = comandoSQL.executeUpdate();

			return (linhasAfetadas > 0);
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao deletar o livro.", erro);
		}	
	}

	// Mapear ResultSet para LivroModel
	private LivroModel mapearResultSetParaLivroModel(ResultSet resultado) {
		LivroModel livroModel = new LivroModel();

		try {

			livroModel.setId(resultado.getInt("livro_id"));
			livroModel.setTitulo(resultado.getString("livro_titulo"));
			livroModel.setAnoPublicacao(resultado.getInt("livro_ano_publicacao"));
			livroModel.setIsbn(resultado.getString("livro_isbn"));

		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao acessar dados do livro.", erro);
		}

		return livroModel;
	}

	// SALVAR (INSERT)
	public LivroSalvarDTO inserir(LivroSalvarDTO livroSalvarDTO)  {
		String sqlLivroAutor = """
			INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)
		""";

		String sqlLivro = """
			INSERT INTO livro (id_categoria, id_editora, titulo, ano_publicacao, isbn) 
			VALUES (?, ?, ?, ?, ?)
		""";

		try (Connection conexao = gerenciadorBancoDados.obterConexao();) {
			
			conexao.setAutoCommit(false);

			try ( PreparedStatement comandoSQLLivro = conexao.prepareStatement(sqlLivro, Statement.RETURN_GENERATED_KEYS);
					PreparedStatement comandoSQLLivroAutor = conexao.prepareStatement(sqlLivroAutor)) {

				comandoSQLLivro.setInt(1, livroSalvarDTO.getCategoriaId());
				comandoSQLLivro.setInt(2, livroSalvarDTO.getEditoraId());
				comandoSQLLivro.setString(3, livroSalvarDTO.getTitulo());
				comandoSQLLivro.setInt(4, livroSalvarDTO.getAnoPublicacao());
				comandoSQLLivro.setString(5, livroSalvarDTO.getIsbn());

				// insere o livro e obtém o id gerado para o livro, 
				// para depois inserir os autores do livro na tabela 
				// livro_autor utilizando o id do livro gerado
				comandoSQLLivro.executeUpdate();

				try (ResultSet chavesGeradas = comandoSQLLivro.getGeneratedKeys()) {
					if (chavesGeradas.next()) {
						int idLivroGerado = chavesGeradas.getInt(1);
						livroSalvarDTO.setId(idLivroGerado);
					} else {
						throw new SQLException("Falha ao inserir o livro");
					}
				}

				for (int autorId : livroSalvarDTO.getAutoresIds()) {
					comandoSQLLivroAutor.setInt(1, livroSalvarDTO.getId());
					comandoSQLLivroAutor.setInt(2, autorId);
					comandoSQLLivroAutor.addBatch();
				}

				comandoSQLLivroAutor.executeBatch();

				conexao.commit();

				return livroSalvarDTO;
			} catch (SQLException erro) {
				conexao.rollback();
				throw new ExcecaoSQL("Erro ao inserir o livro.", erro);
			}
		} catch (SQLException erro) {
			throw new ExcecaoSQL("Erro ao obter conexão com o banco de dados.", erro);
		} 
	}	
}
