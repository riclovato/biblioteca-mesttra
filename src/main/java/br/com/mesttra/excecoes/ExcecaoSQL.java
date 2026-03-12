package br.com.mesttra.excecoes;

import br.com.mesttra.dto.ApiErro;

public class ExcecaoSQL extends RuntimeException{

	public ExcecaoSQL(String mensagem, Throwable causa) {
		super("Identificador de erro: " + ApiErro.getCodigoAleatorio() + ". " + mensagem, causa);
	}

}