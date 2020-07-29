package com.fiap.bot.main;

import com.fiap.bot.exceptions.CouldNotConnectToBotException;
import com.fiap.bot.principal.GerenciadorBotTelegramFIAP;

/**
 * Classe inicial do GerenciadorBotTelegramFIAP que será chamado pela JVM
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class GerenciadorBotFiapMain 
{
	/**
	 * Método main que inicia a aplicação
	 * @param args argumentos para o método.
	 */
    public static void main( String[] args )
    {
    	// Inicia o programa gerenciador de mensagens do Bot do Telegram
    	GerenciadorBotTelegramFIAP gerenciador = new GerenciadorBotTelegramFIAP();
    	try {
    		// Iniciando...
			gerenciador.iniciarBot();
		} catch (CouldNotConnectToBotException e) {
			e.printStackTrace();
		}
    }
}
