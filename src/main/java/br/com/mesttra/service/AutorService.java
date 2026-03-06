package br.com.mesttra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesttra.dao.AutorDAO;
import br.com.mesttra.model.AutorModel;

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
    public AutorModel buscarPorId(Integer id) {
        return autorDAO.buscarPorId(id);
    }

    // BUSCAR POR NOME (parcial)
    public List<AutorModel> buscarPorNome(String nomeParte) {
        return autorDAO.buscarPorNome(nomeParte);
    }

    // SALVAR
    public AutorModel salvar(AutorModel autor) {
        return autorDAO.salvar(autor);
    }

    // ATUALIZAR
    public AutorModel atualizar(Integer id, AutorModel autor) {
        return autorDAO.atualizar(id, autor);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return autorDAO.deletar(id);
    }
}
