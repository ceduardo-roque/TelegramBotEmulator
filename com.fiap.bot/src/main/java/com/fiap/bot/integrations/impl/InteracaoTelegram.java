package com.fiap.bot.integrations.impl;

import com.fiap.bot.integrations.abstracts.AbstractInteracao;
import com.pengrad.telegrambot.model.Update;

/**
 * Classe concreta que representa uma interação de um contato do Telegram com o Bot da Pizzaria
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class InteracaoTelegram extends AbstractInteracao {

	
	private Update update;
	
	/**
	 * Construtor do objeto interação 
	 * @param update Objeto efetivo relativo à interação no Telegram (com.pengrad.telegrambot.model.Update). Este objeto é retorno na consulta via API do Telegram.
	 */
	public InteracaoTelegram(Update update) {
		this.update = update;
	}
	
	/**
	 * Método sobrescrito que recupera a mensagem enviada pelo Telegram por um contato do Telegram
	 */
	@Override
	public String getMensagemEnviadaPeloUsuario() {
		return this.update.message().text();
	}

	/**
	 * Método que retorna o nome do contato que enviou uma mensagem no Telegram
	 */
	@Override
	public String getNomeContatoInteracao() {
		String firstName = this.update.message().from().firstName();
		return firstName;
	}

	/**
	 * Método que recupera o Id do contato que entrou em contato no Telegram
	 */
	@Override
	public long getIdContatoInteracao() {
		return this.update.message().from().id();
	}

	/**
	 * Método que retorna o Objeto Update relativo à interação do Telegram (com.pengrad.telegrambot.model.Update)
	 */
	@Override
	public Object getObjetoManipuladorDaMensagem() {
		return this.update;
	}

	/**
	 * Método que recupera o Id da mensagem enviada no Telegram
	 */
	@Override
	public int getIdMensagem() {
		return this.update.message().messageId();
	}

}
