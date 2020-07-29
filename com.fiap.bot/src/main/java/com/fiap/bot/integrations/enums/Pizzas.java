package com.fiap.bot.integrations.enums;

/**
 * Este Enum é responsavel pelo tipos de Pizzas que serão listados para o cliente.
 * @author Ayton Henrique
 *
 */
public enum Pizzas {
    MUZZARELA("MUZZARELA","25.60"),
    PORTUGUESA("PORTUGUESA","35.60"),
    ATUM("ATUM","15.60"),
    MARGUERITA("MARGUERITA","30.60");
	
    private String nome;
	private String valor;
    
	Pizzas(String nome, String valor) {
		this.nome = nome;
		this.valor = valor;
    }
    public String getNome() {
		return nome;
	}

	public String getValor() {
		return valor;
	}
}