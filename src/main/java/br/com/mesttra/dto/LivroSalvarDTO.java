package br.com.mesttra.dto;

import java.util.List;

public class LivroSalvarDTO {
	int id;
	int categoriaId; 
	int editoraId; 
	String titulo;
	Integer anoPublicacao;
	String isbn;
	List<Integer> autoresIds; 
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public int getEditoraId() {
		return editoraId;
	}

	public void setEditoraId(int editoraId) {
		this.editoraId = editoraId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<Integer> getAutoresIds() {
		return autoresIds;
	}

	public void setAutoresIds(List<Integer> autoresIds) {
		this.autoresIds = autoresIds;
	}

}
