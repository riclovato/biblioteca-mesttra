package br.com.mesttra.dto;

import java.util.List;

public class LivroAdicionarRemoverAutoresDTO {
	private List<Integer> idsAutoresAdicionar; // Lista de IDs dos autores
	private List<Integer> idsAutoresRemover; // Lista de IDs dos autores a serem removidos	

	public List<Integer> getIdsAutoresAdicionar() {
		return idsAutoresAdicionar;
	}

	public void setIdsAutoresAdicionar(List<Integer> idsAutoresAdicionar) {
		this.idsAutoresAdicionar = idsAutoresAdicionar;
	}

	public List<Integer> getIdsAutoresRemover() {
		return idsAutoresRemover;
	}

	public void setIdsAutoresRemover(List<Integer> idsAutoresRemover) {
		this.idsAutoresRemover = idsAutoresRemover;
	}
}
