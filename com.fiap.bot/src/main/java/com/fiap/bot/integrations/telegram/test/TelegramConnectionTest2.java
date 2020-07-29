package com.fiap.bot.integrations.telegram.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fiap.bot.exceptions.CouldNotConnectToBotException;
import com.fiap.bot.integrations.abstracts.AbstractInteracao;
import com.fiap.bot.integrations.impl.TelegramBotImpl;
import com.fiap.bot.jarvis.JarvisBot;
import com.fiap.bot.jarvis.Mensagem;
import com.fiap.bot.principal.GerenciadorBotTelegramFIAP;

/**
 * Classe de testes de interação com o Telegram 2
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class TelegramConnectionTest2 {

	public static void main(String[] args) {
		// 	try {
		// 		// GerenciadorBotTelegramFIAP gerenciador = new GerenciadorBotTelegramFIAP();;
		// 		// while(true) {
		// 		// 	List<AbstractInteracao> interacoes = gerenciador.obtemInteracoesComOBot(10);
		// 		// 	for(AbstractInteracao interacao : interacoes) {

		// 		// 		LocalDateTime dataHoraMensagem = interacao.getDataHoraMensagem();
		// 		// 		System.out.println("Data/Hora da mensagem: " + dataHoraMensagem.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
		// 		// 		System.out.println("ID do Usuario   : " + interacao.getIdContatoInteracao() + " - " + interacao.getNomeContatoInteracao());
		// 		// 		System.out.println("ID da Mensagem  : " + interacao.getIdMensagem());
		// 		// 		String mensagem = interacao.getMensagemEnviadaPeloUsuario();
		// 		// 		System.out.println("Mensagem enviada: " + mensagem);

		// 		// 		String resposta = JarvisBot.IniciarConversa(interacao.getIdContatoInteracao())
		// 		// 				.Responder(new Mensagem(interacao.getIdMensagem(),
		// 		// 				 	interacao.getMensagemEnviadaPeloUsuario(),
		// 		// 					 interacao.getDataHoraMensagem()));
									 
		// 		// 		telegramBotImpl.enviaMensagem(interacao.getIdContatoInteracao(), resposta);

		// 			// 	if(!mensagem.equals("/start") && !mensagem.equals("/previsao") && !mensagem.equals("/pedido") && !mensagem.equals("/localizacao")) {
		// 			// 		interacao.setMensagemResposta("Desculpe... não entendi. Para começar, digite /start.");
		// 			// 		telegramBotImpl.enviaMensagem(interacao);
		// 			// 	} 
						
		// 			// 	if(mensagem.equals("/start")) {
		// 			// 		interacao.setMensagemResposta("Olá! Eu sou o Bot da Pizzaria! Escolha suas opções abaixo:\n"
		// 			// 				+ "/pedido\n"
		// 			// 				+ "/localizacao\n"
		// 			// 				+ "/previsao\n"
		// 			// 				+ "/sair");
							
		// 			// 		telegramBotImpl.enviaMensagem(interacao);
		// 			// 	}
						
		// 			// 	if(mensagem.equals("/previsao")) {
		// 			// 		interacao.setMensagemResposta("O tempo hoje está muito bom para uma pizza!");					
		// 			// 		telegramBotImpl.enviaMensagem(interacao);	
		// 			// 	}
						
		// 			// 	if(mensagem.equals("/localizacao")) {
		// 			// 		interacao.setMensagemResposta("Estou na rua Dr. Lucidio Avelar, 190!!!");					
		// 			// 		telegramBotImpl.enviaMensagem(interacao);	
		// 			// 	}
						
		// 			// 	if(mensagem.equals("/sair")) {
		// 			// 		interacao.setMensagemResposta("Até mais!!!");					
		// 			// 		telegramBotImpl.enviaMensagem(interacao);	
		// 			// 	}
		// 			}	
					 
		// 		}
		// } catch (CouldNotConnectToBotException e) {
		// 	e.printStackTrace();
		// }
	}
	
}
