package com.fiap.bot.jarvis;

import com.fiap.bot.integrations.enums.StatusPedido;

/**
 * Classe Pedido com os atributos para identificar a Pizzaria;
 * @author Ayton Henrique
 *
 */

public class Pedido {
    private String Pizza;
    private String Valor;
    private StatusPedido Status;

    public String getPizza() {
        return Pizza;
    }
    public void setPizza(String pizza) {
        this.Pizza = pizza;
    }

    public StatusPedido getStatus() {
        return Status;
    }

    public void setStatus(StatusPedido status) {
        this.Status = status;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        this.Valor = valor;
    }

    public Pedido(String pizza, String valor, StatusPedido status) {
        setPizza(pizza);
        setValor(valor);
        setStatus(status);
    }
}
