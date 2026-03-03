package br.com.mesttra.controller;

import br.com.mesttra.model.AlunoModel;
import br.com.mesttra.service.AlunoService;

import org.springframework.http.HttpStatus;
// ResponseEntity permite controlar status + headers + body da resposta HTTP.
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

//Anotação que diz ao Spring: esta classe é um controller web REST e seus métodos retornam JSON diretamente
@RestController 
//Anotação para  definir o caminho base (URL base) que o controller vai atender aos requests http.
@RequestMapping("/alunos")
public class AlunoController {

    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    // LISTAR TODOS
    // Anotação para mapear requisições HTTP GET para o método listar(), ou seja
	 // quando alguém fizer um GET nessa URL /alunos/, execute este método
	@GetMapping("/")
    public List<AlunoModel> listar() {
        return service.listarTodos();
    }

    // BUSCAR POR ID
	 // Anotação para mapear requisições HTTP GET para o método buscar(), ou seja
	 // quando alguém fizer um GET nessa URL seguida de um número (ex: /alunos/id/1), execute este método
    @GetMapping("/id/{id}")
    public ResponseEntity<AlunoModel> buscar(@PathVariable("id") Integer id) { //@PathVariable("id") é usado para capturar um valor que vem na URL (no caminho da rota) e colocar esse valor dentro de uma variável do método.

        AlunoModel aluno = service.buscarPorId(id);

        if (aluno == null) {
				// Retorna status 404 Not Found se o aluno não for encontrado
            return ResponseEntity.notFound().build();
        }

		  // Retorna o aluno encontrado com status 200 OK
        return ResponseEntity.ok(aluno);
    }

    // CRIAR
	 // Anotação para mapear requisições HTTP POST (/alunos/) para o método criar(), ou seja
	 // quando alguém fizer um POST nessa URL, execute este método
    @PostMapping("/")
    public AlunoModel criar(@RequestBody AlunoModel aluno) {
        return service.salvar(aluno);
    }

    // ATUALIZAR
	 // Anotação para mapear requisições HTTP PUT para o método atualizar(), ou seja
	 // quando alguém fizer um PUT nessa URL seguida de um número (ex: /alunos/id/1), execute este método
    @PutMapping("/id/{id}")
    public ResponseEntity<AlunoModel> atualizar(@PathVariable("id") Integer id, @RequestBody AlunoModel aluno) {

        AlunoModel atualizado = service.atualizar(id, aluno);

        if (atualizado == null) {
				// Retorna status 404 Not Found se o aluno não for encontrado
            return ResponseEntity.notFound().build();
        }

		  // Retorna o aluno atualizado com status 200 OK
        return ResponseEntity.ok(atualizado);
    }


    // REMOVER
	 // Anotação para mapear requisições HTTP DELETE para o método remover(), ou seja
	 // quando alguém fizer um DELETE nessa URL seguida de um número (ex: /alunos/id/1), execute este método
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Integer id) {
        try {
            boolean removido = service.remover(id);

            if (!removido) {
                // Retorna status 404 Not Found se o aluno não for encontrado
                return ResponseEntity.notFound().build();
            }

            // Retorna status 204 No Content se a remoção for bem-sucedida
            return ResponseEntity.noContent().build();

        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
				// Como aqui pode ocorrer um erro de SQL, retornamos http status 409 Conflict	que é o status 
				// mais adequado para indicar que houve um conflito no servidor (no caso, um erro de banco de dados)
				// e também retornamos uma mensagem de erro no corpo da resposta. 
				// Como o 409 não é um status de erro genérico, o spring nao possui um método específico para retornar esse status, 
				// por isso usamos o método genérico status() e passamos o HttpStatus.CONFLICT como argumento, e depois usamos o método body() 
				// para colocar a mensagem de erro no corpo da resposta, gerando um JSON com a chave "erro" e o valor da mensagem de erro conforme o 
				// exemplo abaixo:
				// {
				//   "erro": "mensagem de erro aqui"
				// }
            return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body(Map.of("erro", msg));

				// Esse padrão .metodo().metodo() é chamado de builder pattern e é muito utilizado para construir objetos complexos, como as respostas HTTP, de forma fluida e legível.
				// Veremos outros exemplos de uso do builder pattern ainda neste projeto
        }
    }
}
