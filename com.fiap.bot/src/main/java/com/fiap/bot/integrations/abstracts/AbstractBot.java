package com.fiap.bot.integrations.abstracts;

import java.util.List;

import com.fiap.bot.exceptions.CouldNotConnectToBotException;

/**
 * Esta classe é a classe que representa um Bot Abstrato. Qualquer Bot para qualquer provedor deverá estendê-la para instanciar um novo Bot para um provedor específico.
 * @author Carlos Eduardo Roque da Silva
 *
 */
public abstract class AbstractBot {
	
	/**
	 * Construtor que deve ser invocado por todas as classes filhas
	 * @throws CouldNotConnectToBotException lança exeção se não encontar a conexao
	 * @param chaveBot numero da chave do bot
	 */
	public AbstractBot(String chaveBot) throws CouldNotConnectToBotException {
		this.conectarBot(chaveBot);
	}

	/**
	 * Método de conexao com o Bot
	 * @param chaveBot A chave para conexão com o Bot
	 * @throws CouldNotConnectToBotException
	 */
	protected abstract void conectarBot(String chaveBot) throws CouldNotConnectToBotException;
	
	/**
	 * Método de envio de mensagem para um usuário que interagiu com o Bot
	 * O objeto interacao (AbstractInteracao ou suas classes filhas) que contém as informações da interação com o Bot
	 * @param idConversa identificador da conversa
	 * @param mensagem Conversa do bot
	 * @return true/false para mensagem enviada
	 */
	public abstract boolean enviaMensagem(Long idConversa,String mensagem);
	
	
	/**
	 * Método que lista as interações com o bot
	 * @param numeroMensagens idetificação pelo numero das mensagens
	 * @return listaDeInteracoes retorna lista com as iterações.
	 */
	public abstract List<AbstractInteracao> obtemInteracoesComOBot(int numeroMensagens);
	
}
