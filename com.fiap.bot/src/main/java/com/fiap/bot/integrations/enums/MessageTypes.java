package com.fiap.bot.integrations.enums;

/**
 * Este Enum é responsavel por classificar os tipos de mensagens possiveis tratados pelo Bot
 * @author Carlos Eduardo Roque da Silva
 *
 */
public enum MessageTypes {
	
	/**
	 * Mensagem de áudio enviada para o Bot
	 */
	AUDIO("Audio", 1);

	private String nome;
	private int valor;

	MessageTypes(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public int getValor() {
		return valor;
	}
	
}
