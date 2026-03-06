package br.com.mesttra.model;

public class AlunoModel {

	private Integer id;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String cep;
	private String estado;
	private String cidade;
	private String endereco;
	private String bairro;

	public AlunoModel() {}

	public AlunoModel(String nome, String cpf, String telefone, String email, String cep, 
				 String estado, String cidade, String endereco, String bairro) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
		this.endereco = endereco;
		this.bairro = bairro;
	}

	public AlunoModel(Integer id, String nome, String cpf, String telefone, String email, 
				 String cep, String estado, String cidade, String endereco, String bairro) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
		this.endereco = endereco;
		this.bairro = bairro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Override
	public String toString() {	
		return "id = " + id + ", nome = " + nome + ", cpf = " + cpf + ", telefone = " + telefone + 
			   ", email = " + email + ", cep = " + cep + ", estado = " + estado + 
			   ", cidade = " + cidade + ", endereco = " + endereco + ", bairro = " + bairro;
	}
}
