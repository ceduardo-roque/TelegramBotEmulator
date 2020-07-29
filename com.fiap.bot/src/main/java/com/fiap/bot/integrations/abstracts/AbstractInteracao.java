package com.fiap.bot.integrations.abstracts;

import java.time.LocalDateTime;

/**
 * Classe abstrata que representa uma interação recebida pela Bot por parte de um contato
 * @author Carlos Eduardo Roque da Silva
 *
 */
public abstract class AbstractInteracao {
	
	private LocalDateTime dataHoraMensagem;
	private String mensagemResposta; // Será usado nas classes filhas para enviar a resposta

	/**
	 * Método que retorna a mensagem enviada pelo contato que interagiu com o Bot
	 * @return A mensagem enviada
	 */
	public abstract String getMensagemEnviadaPeloUsuario();
	
	/**
	 * Método que retorna o nome do contato que interagiu com o Bot
	 * @return O nome do usuario
	 */
	public abstract String getNomeContatoInteracao();
	
	
	/**
	 * Método que retorna o id do contato que interagiu com o Bot
	 * @return O id do usuario que interagiu com o Bot
	 */
	public abstract long getIdContatoInteracao();

	/**
	 * Método que retorna o objeto interno manipulador da interação dentro do Bot
	 * @return O objeto enviado pelo Bot relativo à interação
	 */
	public abstract Object getObjetoManipuladorDaMensagem();
	
	/**
	 * Retorna a mensagem enviada pelo contato
	 * @return mensagemResposta resposta da mensagem enviada pelo bot
	 */
	public String getMensagemResposta() {
		return mensagemResposta;
	}

	/**
	 * Seta a hora em que a mensagem foi enviada pelo contato no objeto Interacao
	 * @param dataHoraMensagem informar a data/hora da mensagem
	 */
	public void setDataHoraMensagem(LocalDateTime dataHoraMensagem) {
		this.dataHoraMensagem = dataHoraMensagem;
	}
	
	/**
	 * Seta a mensagem de resposta a ser enviada para o Bot através do método enviaMensagem da classe AbstractBot
	 * @param mensagemResposta A mensagem a ser enviada
	 */
	public void setMensagemResposta(String mensagemResposta) {
		this.mensagemResposta = mensagemResposta;
	}
	
	/**
	 * Recupera a data e a hora da mensagem que foi enviada
	 * @return dataHoraMensagem retornar a data/hora da mensagem
	 */
	public LocalDateTime getDataHoraMensagem() {
		return dataHoraMensagem;
	}

	/**
	 * Retorna o Id da mensagem enviada pelo contato ao Bot
	 * @return int retorna o id da mensagem.
	 */
	public abstract int getIdMensagem();
}