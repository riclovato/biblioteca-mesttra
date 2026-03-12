package br.com.mesttra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mesttra.dao.EmprestimoDAO;
import br.com.mesttra.model.EmprestimoModel;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class EmprestimoService {

    private final EmprestimoDAO emprestimoDAO;

    public EmprestimoService(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    // LISTAR TODOS
    public List<EmprestimoModel> listarTodos() {
        return emprestimoDAO.buscarTodos();
    }

    // BUSCAR POR ID
    public EmprestimoModel buscarPorId(Integer id) {
        return emprestimoDAO.buscarPorId(id);
    }

    // BUSCAR POR NOME (parcial)
    public List<EmprestimoModel> buscarPorNome(String nomeParte) {
        return emprestimoDAO.buscarPorNome(nomeParte);
    }

    // SALVAR
    public EmprestimoModel salvar(EmprestimoModel emprestimo) {
        try {
            return emprestimoDAO.salvar(emprestimo);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("Erro: ID do aluno ou usuario nao encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar emprestimo");
        }
    }

    // ATUALIZAR
    public EmprestimoModel atualizar(Integer id, EmprestimoModel emprestimo) {
        return emprestimoDAO.atualizar(id, emprestimo);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return emprestimoDAO.deletar(id);
    }
}
