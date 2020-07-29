package com.fiap.bot.principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import com.fiap.bot.constants.Constants;
import com.fiap.bot.exceptions.CouldNotConnectToBotException;
import com.fiap.bot.integrations.abstracts.AbstractInteracao;
import com.fiap.bot.integrations.impl.TelegramBotImpl;
import com.fiap.bot.jarvis.JarvisBot;
import com.fiap.bot.threads.runnables.TratamentoDeMensagensRunnable;

/**
 * Esta classe é responsável por iniciar o programa gerenciador do Bot do
 * Telegram da Pizzaria FIAP
 * 
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class GerenciadorBotTelegramFIAP {

	private Map<Long, AbstractInteracao> mapaDeInteracoes = new HashMap<Long, AbstractInteracao>();
	private Thread threadTratamentoDeMensagens;
	private String mensagem;

	/**
	 * Este método inicia o programa. Ele carrega os recursos necessários para o
	 * funcionamento correto do gerenciador
	 * 
	 * @throws CouldNotConnectToBotException Exceção que ocorre caso não seja
	 *                                       possível se conectar no Bot
	 */
	public void iniciarBot() throws CouldNotConnectToBotException {

		System.out.println("******************************************************************");
		System.out.println("         Gerenciador do Bot do Telegram da Pizzaria FIAP");
		System.out.println("******************************************************************");
		System.out.println("Iniciando conexão com o Bot do Telegram...");

		// Recuperando arquivo params.properties
		System.out.println("Recuperando arquivos de configurações...");
		Properties arquivoConfiguracao = this.recuperaArquivoDeConfiguracoes();

		// Valida se arquivo foi recuperado com sucesso
		if (!this.arquivoDeConfiguracaoValido(arquivoConfiguracao))
			return;

		// Recupera a chave de conexão com o Telegram do arquivo de configuração
		System.out.println("Recuperando chave de conexão com o Bot do Telegram do arquivo de configurações...");
		String chaveBotTelegram = this.recuperaChaveBot(arquivoConfiguracao);

		// Valida se a chave é valida
		if (this.isChaveNullOrEmpty(chaveBotTelegram))
			return;

		// Iniciando a conexão com o Bot e validando se a conexão está válida
		System.out.println("Conectando ao Bot do Telegram. Por favor aguarde...");
		TelegramBotImpl bot = new TelegramBotImpl(chaveBotTelegram);
		boolean connected = bot.isConnected();
		if (connected) {
			System.out.println("Conexão com o Bot realizada com sucesso. Bot Conectado: " + connected);
			this.iniciaThreadDeTratamentoDasMensagensDoTelegram(bot);
			System.out.println("Todos os componentes carregados...\n\n\n");
			this.iniciaMenusDeConsultaDoGerenciador();
		} else {
			System.out.println("Não foi possível se conectar ao Bot.");
		}
	}

	/**
	 * Método que avalia se a chave do Telegram foi preenchida no arquivo de propriedades
	 * @param chaveBotTelegram A chave lida do arquivo de propriedades (ou não!)
	 * @return false para uma chave preenchida, true pra uma chave nula ou vazia
	 */
	private boolean isChaveNullOrEmpty(String chaveBotTelegram) {
		boolean chaveInvalida = chaveBotTelegram == null || chaveBotTelegram.isEmpty();
		if (chaveInvalida)
			System.out.println("Não foi possível recuperar chave do BOT do arquivo de configurações.");
		else
			System.out.println("Chave recuperada.");
		return chaveInvalida;
	}

	/**
	 * Método que avalia se o arquivo de propriedades foi encontrado e se o objeto Properties foi populado
	 * @param arquivoConfiguracao O arquivo de propriedades referente ao arquivo Properties
	 * @return true para um arquivo lido corretamente, false para um arquivo inválido
	 */
	private boolean arquivoDeConfiguracaoValido(Properties arquivoConfiguracao) {
		if (arquivoConfiguracao == null) {
			System.out.println("Não foi possível recuperar o arquivo de configurações.");
			return false;
		}

		System.out.println("Arquivo de configuração recuperado.");
		return true;
	}

	/**
	 * Método que inicia o menu interno desta thread, para consultas sobre as interações com o Bot
	 */
	private void iniciaMenusDeConsultaDoGerenciador() {
		Scanner s = new Scanner(System.in);
		boolean processa = true;
		while (processa) {

			System.out.println("******************************************************************");
			System.out.println("	      Gerenciador do Bot do Telegram da Pizzaria FIAP         ");
			System.out.println("******************************************************************");
			System.out.println("	Escolha uma das opções para iniciar:      ");
			System.out.println("                                              ");
			System.out.println(" 1. Consultar número de interlocutores com conversas tratadas pelo Bot");
			System.out.println(" 2. Consultar número de pizzas vendidas na pizzaria pelo Bot");
			System.out.println(" 3. Consultar total vendido pela pizzaria hoje pelo Bot");
			System.out.println(" 4. Listar os pedidos feitos na pizzaria atendidos pelo Bot");
			System.out.println(" 5. Sair");
			System.out.println("\n");

			if (mensagem != null) {
				System.out.println(mensagem + "\n");
				mensagem = null;
			}

			String valorLido = lerTela(s);
			this.trataLeituraTela(valorLido);
		}
		s.close();
	}

	/**
	 * Metodo que avalia os caracteres digitados no menu do programa
	 * @param valorLido O valor digitado
	 */
	private void trataLeituraTela(String valorLido) {
		switch (valorLido) {
		case "1":
			this.consultaNumeroDeConversasTratadasNoBot();
			break;
		case "2":
			this.consultaNumeroDePedidosFeitosNaPizzaria();
			break;
		case "3":
			this.consultaTotalVendidoPelaPizzaria();
			break;
		case "4":
			this.listarPedidosFeitosNaPizzariaPeloBot();
			break;
		case "5":
			this.finalizaExecucaoDoPrograma();
			break;
		default:
			mensagem = "Digite uma opção válida (1, 2, 3, 4)";
		}

	}

	private void listarPedidosFeitosNaPizzariaPeloBot() {
		JarvisBot.listarPedidosFeitosNaPizzaria();
		System.out.println("\n");
		System.out.println("Listagem finalizada.");
		System.out.println("\n\n");
		
	}

	/**
	 * Método que retorna o total vendido na pizzaria com base nas interações com o Bot
	 */
	private void consultaTotalVendidoPelaPizzaria() {
		double total = JarvisBot.recuperaTotalVendidoPelaPizzaria();
		System.out.println("O valor total dos pedidos feitos por usuários tratados pelo Bot é de: R$ " + String.format("%.2f", total));
		System.out.println("\n\n");
	}

	/**
	 * Método que retorna o número de pedidos feitos na pizzaria
	 */
	private void consultaNumeroDePedidosFeitosNaPizzaria() {
		int size = JarvisBot.recuperaNumeroDePedidosDePizzaEfetuadosViaBot();
		System.out.println("O número de pizzas vendidos pelo Bot é de: " + size);
		System.out.println("\n\n");
	}

	/**
	 * Método que retorna o número de conversas tratadas no Bot
	 */
	private void consultaNumeroDeConversasTratadasNoBot() {
		int size = JarvisBot.recuperaNumeroDeConversasManipuladas();
		System.out.println("O número de conversas com interlocutores tratados pelo Bot é de: " + size);
		System.out.println("\n\n");
	}

	/**
	 * Metodo que finaliza o programa
	 */
	private void finalizaExecucaoDoPrograma() {
		System.exit(0);
	}

	/**
	 * Método que lê os valores da tela
	 * @param s O objeto que le os valores da tela
	 * @return O valor lido da tela
	 */
	private String lerTela(Scanner s) {
		String valorLido = "";
		try {
			valorLido = s.nextLine();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		return valorLido;
	}

	/**
	 * Método que inicia a thread de tratamento das mensagems do Telegram. Esta thread é paralela à thread principal e compartilha o mapa de lista de interações por usuario
	 * @param bot O objeto de manipulacao do Bot
	 */
	private void iniciaThreadDeTratamentoDasMensagensDoTelegram(TelegramBotImpl bot) {
		System.out.println("Iniciando Thread de tratamento de mensagens...");
		this.threadTratamentoDeMensagens = new Thread(new TratamentoDeMensagensRunnable(bot, mapaDeInteracoes));
		this.threadTratamentoDeMensagens.start();
		System.out.println("Thread de tratamento de mensagens inicado.");
	}

	/**
	 * Método que recupera a chave de conexão com o Bot do Telegram
	 * @param arquivoConfiguracao O arquivo de propriedades que deve ter a chave cadastrada
	 * @return A chave de conexao com o Telegram
	 */
	private String recuperaChaveBot(Properties arquivoConfiguracao) {
		return arquivoConfiguracao.getProperty(Constants.CHAVE_BOT_TELEGRAM_ARQUIVO_PROPERTIES);
	}

	/**
	 * Recupera o arquvo de propriedades no diretorio properties dentro do projeto
	 * @return Um objeto Properties com as chaves/valores de configuraçao
	 */
	private Properties recuperaArquivoDeConfiguracoes() {
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(Constants.CAMINHO_ARQUIVO_PROPERTIES);
			props.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel encontrar o arquivo de propriedades.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("Não foi possível acessar/ler o arquivo de propriedades.");
			e.printStackTrace();
			return null;
		}
		return props;
	}

}
