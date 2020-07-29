package com.fiap.bot.jarvis;

import java.time.LocalDateTime;

/**
 * Classe de Mensagem para o bot e definição dos atributos contidos na mensagem.
 * @author Ayton Henrique
 *
 */

public class Mensagem {

    private Integer IdMensagem;
    private String Texto;
    private LocalDateTime DataHora;

    public Mensagem(Integer idMensagem, String texto, LocalDateTime dataHora) {
        setIdMensagem(idMensagem);
        setTexto(texto);
        setDataHora(dataHora);
    }

    public LocalDateTime getDataHora() {
        return DataHora;
    }

    private void setDataHora(LocalDateTime dataHora) {
        this.DataHora = dataHora;
    }

    public String getTexto() {
        return Texto;
    }

    private void setTexto(String mensagem) {
        this.Texto = mensagem;
    }

    public Integer getIdMensagem() {
        return IdMensagem;
    }

    private void setIdMensagem(Integer idMensagem) {
        this.IdMensagem = idMensagem;
    }

}