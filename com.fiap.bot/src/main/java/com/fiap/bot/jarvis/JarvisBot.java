package com.fiap.bot.jarvis;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Classe para iniciar a conversa no chatbot
 * @author Ayton Henrique
 *
 */

public class JarvisBot {

    private static ArrayList<Conversa> _conversas = new ArrayList<Conversa>();

    public static Conversa iniciarConversa(Long idConversa, String Nome) {
        Optional<Conversa> conversa = _conversas.stream().filter(c -> c.getIdConversa().equals(idConversa)).findFirst();

        if (conversa.isPresent()) {
            return conversa.get();
        }
        
        Conversa novaConversa = new Conversa(idConversa, Nome);
        _conversas.add(novaConversa);
        return novaConversa;
    }
    
    /**
     * Método auxiliar que retorna para um chamador o número de conversas manipuladas
     * @return Recupera o numero de conversas manipuladas pelo bot
     */
    public static int recuperaNumeroDeConversasManipuladas() {
    	return JarvisBot._conversas.size();
    }

    /**
     * Método auxiliar que retorna o numero de pedidos feito via Bot
     * @return O numero de Pedidos de pizza feitos via bot
     */
    public static int recuperaNumeroDePedidosDePizzaEfetuadosViaBot() {
    	int retorno = 0;
    	for(Conversa c : JarvisBot._conversas) {
    		for(CompraFinalizada compra : c.getComprasFinalizadas()) {
    			retorno += compra.getPedidosFeitosNestaCompra().size();
    		}
    	}
    	return retorno;
    }
    
    public static void encerrarConversa() {

    }

	public static double recuperaTotalVendidoPelaPizzaria() {
    	double totalVendido = 0;
    	for(Conversa c : JarvisBot._conversas) {
    		for (CompraFinalizada pedidoFinalizado : c.getComprasFinalizadas()) {
    			totalVendido += pedidoFinalizado.getPedidosFeitosNestaCompra().stream().mapToDouble(pedido -> Double.valueOf(pedido.getValor())).sum();				
			}
    	}
    	return totalVendido;
	}

	public static void listarPedidosFeitosNaPizzaria() {
    	for(Conversa c : JarvisBot._conversas) {
    		System.out.println("------------------------------------------------------------------------------------------");    		
    		System.out.println("Cliente: " + c.getNomeCliente());
    		System.out.println("Código da conversa: " + c.getIdConversa());
    		System.out.println("Pedido realizado:");
    		System.out.println("------------------------------------------------------------------------------------------");
    		c.getComprasFinalizadas().forEach(compra -> {
    			System.out.println("Compra realizada para o endereço: " + compra.getEndereco());
    			System.out.println("CEP.:" + compra.getCep());
    			
    			compra.getPedidosFeitosNestaCompra().forEach(pedido -> {
        			System.out.println("      " + pedido.getPizza() + " - " + pedido.getValor());    				
    			});
    		});
    		System.out.println("------------------------------------------------------------------------------------------");
    	}
		
	}

}