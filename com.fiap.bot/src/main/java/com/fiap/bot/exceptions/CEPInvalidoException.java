package com.fiap.bot.exceptions;

/**
 * Classe que representa a exceção quando um CEP invalido é digitado pelo interlocutar do Bot
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class CEPInvalidoException extends Exception {

	public CEPInvalidoException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
