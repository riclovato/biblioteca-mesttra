package br.com.mesttra.controller;

import br.com.mesttra.model.AutorModel;
import br.com.mesttra.service.AutorService;

import org.springframework.http.HttpStatus;
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
import java.util.Optional;

@RestController
@RequestMapping("/autores")
public class AutorController {

	private AutorService service;

	public AutorController(AutorService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<AutorModel>> listarTodos() {
		List<AutorModel> autores = service.listarTodos();

		if (autores.isEmpty() || autores == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(autores);
	}

	@GetMapping("/autor/{autor}")
	public ResponseEntity<?> buscarPorNome(@PathVariable("autor") String autor) {
		try {

			Optional<List<AutorModel>> autores = service.buscarPorAutor(autor);

			if (autores == null || autores.isEmpty()) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(autores);
		} catch (Exception ex) {
			String msg = ex.getMessage();

			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(Map.of("erro", msg));
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Optional<AutorModel>> buscarPorId(@PathVariable("id") Integer id) {
		Optional<AutorModel> autor = service.buscarPorId(id);

		if (autor == null || autor.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(autor);
	}

	@PostMapping("/")
	public ResponseEntity<AutorModel> adicionar(@RequestBody AutorModel autor) {
		AutorModel autorCriado = service.adicionar(autor);

		if (autorCriado == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(autorCriado);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<AutorModel> atualizar(@PathVariable("id") Integer id, @RequestBody AutorModel autor) {

		AutorModel atualizado = service.atualizar(id, autor);

		if (atualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> remover(@PathVariable("id") Integer id) {
		boolean removido = service.remover(id);

		if (!removido) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
