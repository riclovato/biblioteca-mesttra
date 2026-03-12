package br.com.mesttra.dto;

import java.util.List;

public class LivroAtualizarTodosAutoresDTO {
	private List<Integer> idsAutores;

	public List<Integer> getIdsAutores() {
		return idsAutores;
	}

	public void setIdsAutores(List<Integer> idsAutores) {
		this.idsAutores = idsAutores;
	}
}
