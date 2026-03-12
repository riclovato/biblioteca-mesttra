package br.com.mesttra.excecoes;

public class ExcecaoNegocio extends RuntimeException {

	public ExcecaoNegocio(String mensagem) {
		super(mensagem);
	}

}