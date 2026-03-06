package br.com.mesttra.init;
import br.com.mesttra.model.AlunoModel;
import br.com.mesttra.dao.AlunoDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AlunoTesteInicial implements CommandLineRunner {

    @Autowired
    private AlunoDAO alunoDao;

    @Override
    public void run(String... args) throws Exception {
		  // Realizar os testes em todos os metodos DAO
		  	
        System.out.println("\n===== TESTE ALUNO =====");

		  // Constroi um aluno
        AlunoModel aluno = new AlunoModel();

        aluno.setNome("Rogério de Freitas Ribeiro da Silva");
        aluno.setCpf("06164893666");
        aluno.setTelefone("34999763017");
        aluno.setEmail("rogeriofr@gmail.com");
        aluno.setCep("38405070");
        aluno.setEstado("MG");
        aluno.setCidade("Uberlândia");
        aluno.setEndereco("Rua  XYZ, 689");
        aluno.setBairro("TIBERY");

		  // Salva o aluno no banco de dados
        alunoDao.salvar(aluno);

		  // Recupera o ID gerado para o aluno salvo
		  int idGerado = aluno.getId();

        System.out.println("Aluno salvo com ID: " + idGerado);

		  // Limpa a referência para garantir que estamos buscando do banco
		  // objeto é reaproveitar a varfiavel aluno para armazenar o resultado da busca
		  aluno = null; 
		  
		  // Busca o aluno pelo ID
		  aluno = alunoDao.buscarPorId(idGerado);
		  System.out.println("\nAluno encontrado por ID: " + aluno);

		  // Busca o aluno por nome, retornando uma lista (pode haver mais de um aluno com o mesmo nome)
		  List<AlunoModel> alunos = alunoDao.buscarPorNome("Rogério");
		  System.out.println("\nAluno encontrado por nome: " + alunos);

		  // Realiza a atualização do aluno
		  aluno.setNome("Rogério de Freitas Ribeiro da Silva");	
		  aluno.setTelefone("34999999999");
		  alunoDao.atualizar(aluno.getId(), aluno);

		  // Busca o aluno atualizado para verificar as mudanças
		  aluno = alunoDao.buscarPorId(aluno.getId());
		  System.out.println("\nAluno atualizado verifique os campos: " + aluno);

		  // Deleta o aluno do banco de dados
		  boolean deletado = alunoDao.deletar(aluno.getId());
		  System.out.println("\nAluno deletado: " + deletado);
    }
}