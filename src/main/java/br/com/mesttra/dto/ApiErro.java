package br.com.mesttra.dto;

import java.util.UUID;

public class ApiErro {
	private String classeErro;
	private String mensagemErro;
	private String rota;

	public ApiErro(int status, String classeErro, String mensagemErro, String rota) {
		this.rota = rota;
		this.classeErro = classeErro;
		this.mensagemErro = mensagemErro;
	}

	public String getClasseErro() {
		return classeErro;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public String getRota() {
		return rota;
	}

	
	public static String getCodigoAleatorio() {
		//gera um código aleatório para identificar o erro, 
		//usado para rastrear logs e identificar o erro nos logs

		return UUID.randomUUID().toString();
	}
}
