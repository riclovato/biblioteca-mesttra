package br.com.mesttra.service;

import br.com.mesttra.dao.AutorDAO;
import br.com.mesttra.model.AutorModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

	private final AutorDAO autorDAO;

	public AutorService(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}

	// LISTAR TODOS
	public List<AutorModel> listarTodos() {
		return autorDAO.buscarTodos();
	}

	// BUSCAR POR ID
	public Optional<AutorModel> buscarPorId(Integer id)  {
		return autorDAO.buscarPorId(id);
	}

	// BUSCAR POR NOME (parcial)
	public Optional<List<AutorModel>> buscarPorAutor(String nomeParte)  {
		return autorDAO.buscarPorAutor(nomeParte);
	}

	// SALVAR
	public AutorModel adicionar(AutorModel aluno)  {
		return autorDAO.inserir(aluno);
	}

	// ATUALIZAR
	public AutorModel atualizar(Integer id, AutorModel aluno)  {
		return autorDAO.atualizar(id, aluno);
	}

	// REMOVER
	public boolean remover(Integer id)  {
		return autorDAO.deletar(id);
	}
}
