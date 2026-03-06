package br.com.mesttra.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.mesttra.dao.AutorDAO;
import br.com.mesttra.model.AutorModel;

@Component
public class AutorTesteInicial implements CommandLineRunner {

    @Autowired
    private AutorDAO autorDao;

    @Override
    public void run(String... args) throws Exception {
        // Realizar os testes em todos os metodos DAO

        System.out.println("\n===== TESTE Autor =====");

        // Constroi um aluno
        AutorModel autor = new AutorModel();

        autor.setAutor("Rogério de Freitas Ribeiro da Silva");
        autor.setPseudonimo("Rogério Ribeiro");
        autor.setNacionalidade("Brasileira");
        autor.setEnderecoWeb("https://www.rogerioribeiro.com");
        autor.setEmail("rogeriofr@gmail.com");
        autor.setTelefone("34999763017");

        autorDao.salvar(autor);

        // Recupera o ID gerado para o autor salvo
        int idGerado = autor.getId();

        System.out.println("Autor salvo com ID: " + idGerado);

        // Limpa a referência para garantir que estamos buscando do banco
        // objeto é reaproveitar a varfiavel aluno para armazenar o resultado da busca
        autor = null;

        // Busca o autor pelo ID
        autor = autorDao.buscarPorId(idGerado);
        System.out.println("\nAutor encontrado por ID: " + autor);

        // Busca o autor por nome, retornando uma lista (pode haver mais de um autor com
        // o mesmo nome)
        List<AutorModel> autores = autorDao.buscarPorNome("Rogério");
        System.out.println("\nAutor encontrado por nome: " + autores);

        // Realiza a atualização do aluno
        autor.setAutor("Rogério de Freitas Ribeiro da Silva");
        autor.setTelefone("34999999999");
        autorDao.atualizar(autor.getId(), autor);

        // Busca o aluno atualizado para verificar as mudanças
        autor = autorDao.buscarPorId(autor.getId());
        System.out.println("\nAluno atualizado verifique os campos: " + autor);

        //Deleta o aluno do banco de dados
        boolean deletado = autorDao.deletar(autor.getId());
        System.out.println("\nAutor deletado: " + deletado);
    }
}