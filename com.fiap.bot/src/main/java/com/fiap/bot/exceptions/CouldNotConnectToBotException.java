package com.fiap.bot.exceptions;

/**
 * Exceção lançada quando a conexão com o Bot não pode ser estabelecida
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class CouldNotConnectToBotException extends Exception {

	public CouldNotConnectToBotException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = 1055486148022810945L;

}
