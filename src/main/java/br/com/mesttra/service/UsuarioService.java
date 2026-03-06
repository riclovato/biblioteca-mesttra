package br.com.mesttra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesttra.dao.UsuarioDAO;
import br.com.mesttra.model.UsuarioModel;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // LISTAR TODOS
    public List<UsuarioModel> listarTodos() {
        return usuarioDAO.buscarTodos();
    }

    // BUSCAR POR ID
    public UsuarioModel buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }

    // BUSCAR POR NOME (parcial)
    public List<UsuarioModel> buscarPorNome(String nomeParte) {
        return usuarioDAO.buscarPorNome(nomeParte);
    }

    // SALVAR
    public UsuarioModel salvar(UsuarioModel usuario) {
        return usuarioDAO.salvar(usuario);
    }

    // ATUALIZAR
    public UsuarioModel atualizar(Integer id, UsuarioModel usuario) {
        return usuarioDAO.atualizar(id, usuario);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return usuarioDAO.deletar(id);
    }
}
