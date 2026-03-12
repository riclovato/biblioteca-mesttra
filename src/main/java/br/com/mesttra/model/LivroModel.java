package br.com.mesttra.model;

import java.util.ArrayList;
import java.util.List;

public class LivroModel {
	private int id;
	private String titulo;
	private int anoPublicacao;
	private String isbn;
	private CategoriaModel categoria;
	private EditoraModel editora;
	private List<AutorModel> autores = new ArrayList<>(); // Lista de autores associados ao livro

	public LivroModel() {
	}

	public LivroModel(CategoriaModel categoria, EditoraModel editora, String titulo, int anoPublicacao,
			String isbn) {
		this.categoria = categoria;
		this.editora = editora;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
		this.isbn = isbn;
	}

	public LivroModel(int id, CategoriaModel categoria, EditoraModel editora, String titulo, int anoPublicacao,
			String isbn) {
		this.id = id;
		this.categoria = categoria;
		this.editora = editora;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
		this.isbn = isbn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public EditoraModel getEditora() {
		return editora;
	}

	public void setEditora(EditoraModel editora) {
		this.editora = editora;
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

	public List<AutorModel> getAutores() {
		// retorna uma copia imutável da lista de autores para evitar
		// alterações na lista original mantendo o encapsulamento
		// da classe LivroModel

		return List.copyOf(autores);
	}

	public void setAutor(AutorModel autor) {
		this.autores.add(autor);
	}

	public void setAutores(List<AutorModel> autores) {
		// Boa pratica de programação, se receber uma lista de autores
		// itera sobre ela e adiciona cada autor na lista de autores
		// atraves do método setAutor(AutorModel autor), para manter a
		// lógica de adição de autores com as validações do método setAutor
		// caso existam.
		for (AutorModel autor : autores) {
			this.autores.add(autor);
		}
	}

	@Override
	public String toString() {
		return "==> LivroModel: id=" + id + ", titulo=" + titulo + ", anoPublicacao=" + anoPublicacao + ", isbn=" + isbn +
				"\nautores:" + autores.toString() + "\ncategoria:" + categoria.toString() + ", editora\n"
				+ editora.toString();
	}
}
