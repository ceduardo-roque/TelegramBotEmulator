package com.fiap.bot.exceptions;

/**
 * Exceção lançada quando uma mensagem não suportada é enviada pelo usuário ao Bot
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class NotSupportedMessageTypeException extends Exception {

	public NotSupportedMessageTypeException(String nome) {
		super("Não é possível tratar mensagens de " + nome);
	}
	
}
