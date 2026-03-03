package br.com.mesttra.service;

import br.com.mesttra.dao.AlunoDAO;
import br.com.mesttra.model.AlunoModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoDAO alunoDAO;

    public AlunoService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    // LISTAR TODOS
    public List<AlunoModel> listarTodos() {
        return alunoDAO.buscarTodos();
    }

    // BUSCAR POR ID
    public AlunoModel buscarPorId(Integer id) {
        return alunoDAO.buscarPorId(id);
    }

    // SALVAR
    public AlunoModel salvar(AlunoModel aluno) {
        return alunoDAO.salvar(aluno);
    }

    // ATUALIZAR
    public AlunoModel atualizar(Integer id, AlunoModel aluno) {
        return alunoDAO.atualizar(id, aluno);
    }

    // REMOVER
    public boolean remover(Integer id) {
        return alunoDAO.deletar(id);
    }
}
