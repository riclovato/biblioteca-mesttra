package br.com.mesttra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesttra.dao.CategoriaDAO;
import br.com.mesttra.model.CategoriaModel;

@Service
public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // LISTAR TODOS
    public List<CategoriaModel> listarTodos() {
        return categoriaDAO.buscarTodos();
    }

    // BUSCAR POR ID
    public CategoriaModel buscarPorId(Integer id) {
        return categoriaDAO.buscarPorId(id);
    }

    // BUSCAR POR NOME (parcial)
    public List<CategoriaModel> buscarPorNome(String nomeParte) {
        return categoriaDAO.buscarPorNome(nomeParte);
    }

    // SALVAR
    public CategoriaModel salvar(CategoriaModel categoria) {
        return categoriaDAO.salvar(categoria);
    }

    // ATUALIZAR
    public CategoriaModel atualizar(Integer id, CategoriaModel categoria) {
        return categoriaDAO.atualizar(id, categoria);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return categoriaDAO.deletar(id);
    }
}
