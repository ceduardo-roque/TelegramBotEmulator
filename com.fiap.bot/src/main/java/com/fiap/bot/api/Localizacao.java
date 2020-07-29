package com.fiap.bot.api;

/**
 * Classe representando a Localização
 * @author Willian
 *
 */
public class Localizacao {
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String numero;
	
	/**
	 * Retorna o CEP
	 * @return o numero do CEP
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * Seta o CEP
	 * @param cep informa o numero do cep
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * Retorna o Logradouro
	 * @return o nome do logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}
	
	/**
	 * Seta o Logradouro
	 * @param logradouro informa o logradouro
	 * 
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	/**
	 * Retorna o Bairro
	 * @return bairro retorna o bairro
	 */
	public String getBairro() {
		return bairro;
	}
	
	/**
	 * Seta o Bairro
	 * @param bairro descreve o bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	/**
	 * Retorna a Cidade
	 * @return cidade nome da cidade
	 */
	public String getCidade() {
		return cidade;
	}
	
	/**
	 * Seta a Cidade
	 * @param cidade recebe o nome da cidade
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	/**
	 * Retorna o Estado
	 * @return estado retorna a sigla do Estado
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Seta o Estado
	 * @param estado informar sigla do estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Retorna o numero
	 * @return numero informa o numero do ensdereço
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Seta o numero
	 * @param numero informra o numero do endereço
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Retorna as informações da Localização
	 */
	@Override
	public String toString(){
		return "CEP: " + this.cep + " | Estado: " + this.estado + " | Cidade: " + this.cidade + " | Bairro: " + this.bairro +
				" | Logradouro: " + this.logradouro;
		
		
	}

}
