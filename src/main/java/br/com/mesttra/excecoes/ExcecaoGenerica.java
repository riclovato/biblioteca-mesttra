package br.com.mesttra.excecoes;

import br.com.mesttra.dto.ApiErro;

public class ExcecaoGenerica extends RuntimeException{

	public ExcecaoGenerica(String mensagem, Throwable causa) {
		super("Identificador de erro: " + ApiErro.getCodigoAleatorio() + ". " + mensagem, causa);
	}

}