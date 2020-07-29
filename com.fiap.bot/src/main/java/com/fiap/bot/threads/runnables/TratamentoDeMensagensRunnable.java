package com.fiap.bot.threads.runnables;

import java.util.List;
import java.util.Map;

import com.fiap.bot.integrations.abstracts.AbstractInteracao;
import com.fiap.bot.integrations.impl.TelegramBotImpl;
import com.fiap.bot.jarvis.JarvisBot;
import com.fiap.bot.jarvis.Mensagem;

/**
 * Classe Runnable que trata a execução da leitura das interações com o Bot
 * 
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class TratamentoDeMensagensRunnable implements Runnable {

	/**
	 * O objeto de interação com o Bot
	 */
	private TelegramBotImpl bot;

	/**
	 * O mapa de interações compartilhado com a Thread Principal
	 */
	private Map<Long, AbstractInteracao> mapaDeConversasPorUsuario;

	/**
	 * Construtor que recebe o objeto de interação com o Bot do Telegram e o mapa de
	 * interações por usuario da thread principal
	 * 
	 * @param bot                       O objeto do tipo TelegramBotImpl com a
	 *                                  conexão ativa
	 * @param mapaDeConversasPorUsuario O hashmap da lista de interaçoes por usuário
	 */
	public TratamentoDeMensagensRunnable(TelegramBotImpl bot, Map<Long, AbstractInteracao> mapaDeConversasPorUsuario) {
		this.bot = bot;
		this.mapaDeConversasPorUsuario = mapaDeConversasPorUsuario;
	}

	/**
	 * Método sobrescrito que roda eternamente a cada 1 segundo onde ele lê as
	 * interações com o Bot de 10 em 10, trata uma a uma e atualiza o mapa de
	 * interações por usuario da thread principal
	 */
	@Override
	public void run() {
		while (true) {
			// Recupera as ultimas 10 interações não lidas pelo Bot
			List<AbstractInteracao> interacoes = this.bot.obtemInteracoesComOBot(10);
			// Itera uma a uma
			for (AbstractInteracao interacao : interacoes) {
				// Recupera Id do Usuario Telegram que interagiu
				long idConversa = interacao.getIdContatoInteracao();
				// Invoca o JarvisBot para tratativa das mensagens e geração das respostas para cada status e situação da máquina de estados
				JarvisBot.iniciarConversa(idConversa, interacao.getNomeContatoInteracao())
						.responder(new Mensagem(interacao.getIdMensagem(), interacao.getMensagemEnviadaPeloUsuario(), interacao.getDataHoraMensagem()))
						.forEach(r -> {
							this.bot.enviarDigitando(idConversa);
							this.bot.enviaMensagem(idConversa, r);			
						});
			}
			try {
				// Espera 2s antes de iniciar uma nova consulta
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("Não foi possível fazer a thread esperar. " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
