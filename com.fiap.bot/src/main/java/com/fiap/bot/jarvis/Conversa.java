package com.fiap.bot.jarvis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fiap.bot.api.Localizacao;
import com.fiap.bot.api.LocalizacaoApi;
import com.fiap.bot.exceptions.CEPInvalidoException;
import com.fiap.bot.integrations.enums.Intencoes;
import com.fiap.bot.integrations.enums.Pizzas;
import com.fiap.bot.integrations.enums.StatusPedido;

/**
 * Classe de interaçao com o Cliente no telegram para identificar as mensagens
 * enviadas pelo cliente.
 * 
 * @author Ayton Henrique, Aruna Fernanda, Sara, Carlos Eduardo Roque da Silva
 *
 */
public class Conversa {
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<CompraFinalizada> comprasFinalizadas = new ArrayList<CompraFinalizada>();

	private String NomeCliente;
	private Long IdConversa;

	/**
	 * Método que retorna a lista de compras finalizadas pelo Bot em uma conversa
	 * @return A lista de compras finalizadas
	 */
	public List<CompraFinalizada> getComprasFinalizadas() {
		return Collections.unmodifiableList(this.comprasFinalizadas);
	}

	/**
	 * Retorna a lista de mensagens tratadas pelo Bot nesta conversa
	 * @return A lista de mensagens
	 */
	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	/**
	 * Método que retorna o nome do cliente desta conversa
	 * @return O nome do cliente
	 */
	public String getNomeCliente() {
		return NomeCliente;
	}

	/**
	 * Método que retorna o id desta conversa
	 * @return O id desta conversa
	 */
	public Long getIdConversa() {
		return this.IdConversa;
	}

	public Conversa(Long idConversa, String NomeCliente) {
		this.IdConversa = idConversa;
		this.NomeCliente = NomeCliente;
	}

	/**
	 * Método de resposta aos pedidos solicitados pelo cliente;
	 * 
	 * @param mensagem mensagens enviadas do bot
	 * @return respostas retorna uma lista de mensagens
	 */

	public ArrayList<String> responder(Mensagem mensagem) {
		// Array de respostas para serem enviadas ao final das definições de ação
		ArrayList<String> respostas = new ArrayList<String>();
		// Valida se já existe um pedido em andamento e o recupera
		Optional<Pedido> _pedidoAbertoAndamento = pedidos.stream()
				.filter(x -> x.getStatus() == StatusPedido.ABERTO || x.getStatus() == StatusPedido.ANDAMENTO)
				.findFirst();
		// Recupera a mensagem
		String _mensagem = mensagem.getTexto();

		// Verifica a intenção do usuário
		Intencoes _intencao = Intencao.identificar(_mensagem);

		if (_intencao == Intencoes.INTENCAO_DESCONHECIDA && !_pedidoAbertoAndamento.isPresent()) {
			respostas.add("Desculpe. Não entendi. Poderia digitar novamente ou digitar /start para começar?");
		} else {

			// Valida e norteia as possiveis escolhas do usuário

			// Valida se o interlocutor digitou comandos fora da hora ou fora de ordem
			if (this.validaNovoPedidoForaDeHora(_pedidoAbertoAndamento, _intencao, respostas)
					|| this.validaConfirmacaoDoPedidoForaDeHora(_pedidoAbertoAndamento, _intencao, respostas)
					|| this.validaFinalizaPedidoForaDeHora(_pedidoAbertoAndamento, _intencao, respostas)
					|| this.validaAlteracaoDoPedidoForaDeHora(_pedidoAbertoAndamento, _intencao, respostas)
					|| this.validaPedidoForaDoMenu(_pedidoAbertoAndamento, _intencao, respostas, _mensagem)
					|| this.validaRecomecaPedidoForaDeHora(_pedidoAbertoAndamento, _intencao, respostas)) {

			} else {

				// Aparentemente o interlocutor digitou os comandos certos
				this.recuperaCEPeFinalizaPedido(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);

				this.validaNovoPedido(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.validaPedidoEmAndamento(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.validaConfirmacaoDoPedido(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.finalizaPedido(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.alteracaoDePedido(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.adicionarNovasPizzas(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
				this.recomecarPedidoNovamente(respostas, _pedidoAbertoAndamento, _mensagem, _intencao);
			}

		}
		mensagens.add(mensagem);

		return respostas;
	}

	private boolean validaPedidoForaDoMenu(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas, String produto) {
		if (((_intencao == Intencoes.INTENCAO_DESCONHECIDA) && _pedidoAbertoAndamento.isPresent()
				&& _pedidoAbertoAndamento.get().getStatus() != StatusPedido.ANDAMENTO)
				|| (pedidos.isEmpty() && _intencao == Intencoes.INTENCAO_DESCONHECIDA)) {
			respostas.add("Infelizmente o produto \"" + produto
					+ "\" não está no nosso menu. Poderia escolher um item do menu abaixo?");
			respostas.add("Veja o nosso menu:");
			this.imprimeMenu(respostas);
			return true;
		}
		return false;
	}

	/*
	 * Testes OK
	 */
	private boolean validaRecomecaPedidoForaDeHora(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas) {
		if (((_intencao == Intencoes.ESCOLHER_PIZZAS_NOVAMENTE) && !_pedidoAbertoAndamento.isPresent())
				|| (pedidos.isEmpty() && _intencao == Intencoes.ESCOLHER_PIZZAS_NOVAMENTE)) {
			respostas.add("Entendi. Mas eu ainda nao tenho nada anotado neste pedido.");
			respostas.add(
					"Vamos primeiro começar as escolher! Depois você poderá mudar, caso mude de opinião! Vamos lá?");
			respostas.add("Veja o nosso menu:");
			this.imprimeMenu(respostas);
			return true;
		}
		return false;
	}

	/*
	 * Testes OK
	 */
	private boolean validaAlteracaoDoPedidoForaDeHora(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas) {
		if (((_intencao == Intencoes.ALTERAR_PEDIDO) && !_pedidoAbertoAndamento.isPresent())
				|| (pedidos.isEmpty() && _intencao == Intencoes.ALTERAR_PEDIDO)) {
			respostas.add("Para alterar o seu pedido, primeiro inicie a escolha das suas pizzas. Vamos lá?");
			respostas.add("Veja o nosso menu:");
			this.imprimeMenu(respostas);
			return true;
		}
		return false;
	}

	/*
	 * Testes OK
	 */
	private boolean validaFinalizaPedidoForaDeHora(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas) {
		if ((_intencao == Intencoes.FINALIZAR_PEDIDO && !_pedidoAbertoAndamento.isPresent())
				|| (pedidos.isEmpty() && _intencao == Intencoes.FINALIZAR_PEDIDO)) {
			respostas.add("Para finalizar o seu pedido, primeiro inicie a escolha das suas pizzas. Vamos lá?");
			respostas.add("Veja o nosso menu:");
			this.imprimeMenu(respostas);
			return true;
		}

		return false;
	}

	/*
	 * Testes OK
	 */
	private boolean validaConfirmacaoDoPedidoForaDeHora(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas) {

		if ((_intencao == Intencoes.CONFIRMAR_PEDIDO && !_pedidoAbertoAndamento.isPresent())
				|| (pedidos.isEmpty() && _intencao == Intencoes.CONFIRMAR_PEDIDO)) {
			respostas.add("Para confirmar o seu pedido, primeiro inicie a escolha das suas pizzas. Vamos lá?");
			respostas.add("Veja o nosso menu:");
			this.imprimeMenu(respostas);
			return true;
		}

		return false;
	}

	/*
	 * Testes OK
	 */
	private boolean validaNovoPedidoForaDeHora(Optional<Pedido> _pedidoAbertoAndamento, Intencoes _intencao,
			ArrayList<String> respostas) {
		if ((_intencao == Intencoes.NOVO_PEDIDO) && _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Você já está no meio de um pedido. Para recomeçar, digite /escolherNovamente");
			return true;
		}
		return false;
	}

	/**
	 * Método que envia o menu de pizzas para o usuário na resposta
	 * 
	 * @param respostas A lista de respostas a ser enviadas para o usuário
	 */
	private void imprimeMenu(ArrayList<String> respostas) {
		String menu = "";
		for (Pizzas pizza : Pizzas.values()) {
			menu += "/" + pizza + "   ";
		}
		respostas.add("Opções de Pizza: " + menu.trim());
		respostas.add("Outras opções:  /alterarPedido");
	}

	/**
	 * Método que valida se o usuário quer recomeçar o pedido
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void recomecarPedidoNovamente(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.ESCOLHER_PIZZAS_NOVAMENTE
				&& _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Ok! Vamos recomeçar! Escolha novamente as suas pizzas:");
			this.pedidos.clear();
			this.imprimeMenu(respostas);
		}

	}

	/**
	 * Método qeu avalia a escolha do usuário de incluir novas pizzas no pedido
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void adicionarNovasPizzas(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.ADICIONAR_PIZZAS && _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Ok! Escolha mais pizzas para o seu pedido.");
			this.imprimeMenu(respostas);
		}

	}

	/**
	 * Método que avalia se o usuário quer fazer alteraçã no pedido já feito
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void alteracaoDePedido(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.ALTERAR_PEDIDO && _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Ok! O que você gostaria de alterar?");
			respostas.add("/adicionarPizzas ou /escolherNovamente?");
		}

	}

	/**
	 * Método que recupera o CEP do usuário e trata possíveis erros de digitação.
	 * Também consulta o CEP para pegar os dados do endereço na API de Endereços.
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void recuperaCEPeFinalizaPedido(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (_pedidoAbertoAndamento.isPresent() && _pedidoAbertoAndamento.get().getStatus() == StatusPedido.ANDAMENTO) {
			String cep = Intencao.obterCEP(_mensagem);

			if (cep.length() == 0) {
				respostas.add("Desculpe. Acho que há algum problema com o CEP digitado. Poderia digitar novamente?");
			} else {
				try {
					LocalizacaoApi localizacaoAPI = new LocalizacaoApi();
					Localizacao _localizacao = localizacaoAPI.consultaCEP(cep);

					respostas.add("Você está em:");
					String endereco = _localizacao.getLogradouro() + " - " + _localizacao.getBairro() + " "
							+ _localizacao.getCidade() + "/" + _localizacao.getEstado();
					respostas.add(endereco);
					respostas.add("Seu pedido já está sendo preparado.");
					respostas.add("Clique em /start para fazer um novo pedido.");

					// Limpa a lista de pedidos atuais para permitir um novo pedido
					_pedidoAbertoAndamento.get().setStatus(StatusPedido.FECHADO);
					// Adiciona o pedido atual na lista de pedidos passados desta conversa
					CompraFinalizada compraFinalizada = new CompraFinalizada();
					compraFinalizada.setCEPEntrega(cep);
					compraFinalizada.setEnderecoEntrega(endereco);
					compraFinalizada.setPedidosFeitosNestaCompra(this.pedidos);
					this.comprasFinalizadas.add(compraFinalizada);
					this.pedidos.clear();
				} catch (CEPInvalidoException e) {
					respostas.add("Desculpe-nos. Ocorreu um problema na busca do seu endereço.");
					respostas.add(e.getMessage());
					respostas.add("Poderia tentar novamente?.");
				} catch (Exception e) {
					respostas.add("Desculpe-nos. Ocorreu um problema na busca do seu endereço.");
					respostas.add("Por favor, tente novamente em alguns instantes.");
				}
			}
		}

	}

	/**
	 * Método que fecha o pedido e o coloca em andamento. Também recupera o CEP do
	 * contato.
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void finalizaPedido(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento, String _mensagem,
			Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.FINALIZAR_PEDIDO && _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Pedido fechado. Poderia me informar o seu CEP ?");
			_pedidoAbertoAndamento.get().setStatus(StatusPedido.ANDAMENTO);
		}
	}

	/**
	 * Método que valida e confirma se o usuário concorda com o pedido
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void validaConfirmacaoDoPedido(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.CONFIRMAR_PEDIDO && _pedidoAbertoAndamento.isPresent()) {
			respostas.add("Legal! Já vou fechar pra você!");
			respostas.add("O seu pedido ficou assim:");
			String pizzas = "";
			double total = 0.0;
			for (Pedido p : pedidos) {
				pizzas += "Uma pizza: " + p.getPizza() + " no valor de " + p.getValor() + "\n";
				total += Double.parseDouble(p.getValor());
			}
			respostas.add(pizzas);
			respostas.add("Valor total: R$ " + String.format("%.2f", total));
			respostas.add("Tudo certo? Podemos finalizar o pedido?");
			respostas.add("Escolha entre: /finalizarPedido ou /alterarPedido");
		}

	}

	/**
	 * Método que valida o se o usuário deseja continuar a escolher as pizzas
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void validaPedidoEmAndamento(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (_intencao == Intencoes.PEDIDO_EM_ANDAMENTO) {

			Pizzas p = Pizzas.valueOf(_mensagem.replace('/', ' ').trim());
			Pedido _pedido = new Pedido(p.getNome(), p.getValor(), StatusPedido.ABERTO);

			respostas.add("Você escolheu a pizza de " + _pedido.getPizza() + " no valor de " + _pedido.getValor());
			respostas.add("Muito boa opção! Anotei! Gostaria de pedir mais alguma coisa? ");
			respostas.add("Caso contrário, /confirmarPedido");
			this.imprimeMenu(respostas);
			pedidos.add(_pedido);
		}

	}

	/**
	 * Método que valida se o usuário tem a intenção de fazer um novo pedido
	 * 
	 * @param respostas              A lista de respostas que será enviada para o
	 *                               cliente
	 * @param _pedidoAbertoAndamento O pedido atual em andamento, caso exista
	 * @param _mensagem              A mensagem digitada pelo usuario no chat
	 * @param _intencao              A intenção do usuário com base na mensagem
	 *                               digitada
	 */
	private void validaNovoPedido(ArrayList<String> respostas, Optional<Pedido> _pedidoAbertoAndamento,
			String _mensagem, Intencoes _intencao) {
		if (Intencao.identificar(_mensagem) == Intencoes.NOVO_PEDIDO && !_pedidoAbertoAndamento.isPresent()) {
			respostas.add("Seja bem-vindo " + this.NomeCliente + ", qual o sabor de pizza que você deseja?");
			respostas.add("Escolha um dos sabores:");
			this.imprimeMenu(respostas);
		}

	}

}