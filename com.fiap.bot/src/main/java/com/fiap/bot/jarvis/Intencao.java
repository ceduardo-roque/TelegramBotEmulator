package com.fiap.bot.jarvis;

import com.fiap.bot.integrations.enums.Intencoes;
import com.fiap.bot.integrations.enums.Pizzas;
/**
 * Classe de intenções para identificar o status do pedido enviado para o cliente;
 * @author Ayton Henrique
 *
 */

public class Intencao {
	/**
	 * Método para identificar o status do pedido de acordo com o comando enviado
	 * @param mensagem do bot
	 * @return intencoes com os status do pedido
	 */
    public static Intencoes identificar(String mensagem) {
        // TODO: REGEX
        if (mensagem.equalsIgnoreCase("/confirmarPedido")) {
            return Intencoes.CONFIRMAR_PEDIDO;
        }

        for (Pizzas pizza : Pizzas.values()) {
            if(mensagem.trim().equalsIgnoreCase("/" + pizza)) return Intencoes.PEDIDO_EM_ANDAMENTO;
        }

        if (mensagem.equalsIgnoreCase("/finalizarPedido")) {
            return Intencoes.FINALIZAR_PEDIDO;
        }
        
        if(mensagem.equalsIgnoreCase("/alterarPedido")) {
        	return Intencoes.ALTERAR_PEDIDO;
        }
        
        if(mensagem.equalsIgnoreCase("/escolherNovamente")) {
        	return Intencoes.ESCOLHER_PIZZAS_NOVAMENTE;
        }
        
        if(mensagem.equalsIgnoreCase("/adicionarPizzas")) {
        	return Intencoes.ADICIONAR_PIZZAS;
        }

        if(mensagem.equalsIgnoreCase("/start")) {
        	return Intencoes.NOVO_PEDIDO;
        }
        
        return Intencoes.INTENCAO_DESCONHECIDA;
    }

    /**
	 * Método para efetuar a busca do endereço pelo cep informado pelo cliente.
	 * @param mensagem cep informado na mensagem do bot
	 * @return numeracao do cep
	 */
    public static String obterCEP(String mensagem) {
    	String padrao1 = "\\d\\d\\d\\d\\d-\\d\\d\\d";
    	String padrao2 = "\\d\\d\\d\\d\\d\\d\\d\\d";
    	
    	if(mensagem.matches(padrao1) || mensagem.matches(padrao2)) return mensagem;
    	
    	return "";
    }
}