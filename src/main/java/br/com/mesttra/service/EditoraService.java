package br.com.mesttra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesttra.dao.EditoraDAO;
import br.com.mesttra.model.EditoraModel;

@Service
public class EditoraService {

    private final EditoraDAO editoraDAO;

    public EditoraService(EditoraDAO editoraDAO) {
        this.editoraDAO = editoraDAO;
    }

    // LISTAR TODOS
    public List<EditoraModel> listarTodos() {
        return editoraDAO.buscarTodos();
    }

    // BUSCAR POR ID
    public EditoraModel buscarPorId(Integer id) {
        return editoraDAO.buscarPorId(id);
    }

    // BUSCAR POR NOME (parcial)
    public List<EditoraModel> buscarPorNome(String nomeParte) {
        return editoraDAO.buscarPorNome(nomeParte);
    }

    // SALVAR
    public EditoraModel salvar(EditoraModel editora) {
        return editoraDAO.salvar(editora);
    }

    // ATUALIZAR
    public EditoraModel atualizar(Integer id, EditoraModel editora) {
        return editoraDAO.atualizar(id, editora);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return editoraDAO.deletar(id);
    }
}
