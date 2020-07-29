package com.fiap.bot.integrations.enums;

/**
 * Este Enum é responsavel por classificar os status do Pedido solicitado pelo cliente.
 * @author Ayton Henrique
 *
 */
public enum Intencoes {
    NOVO_PEDIDO,
    PEDIDO_EM_ANDAMENTO,
    CONFIRMAR_PEDIDO,
    ALTERAR_PEDIDO,
    CONFIRMAR_ENDERECO,
    FINALIZAR_PEDIDO, 
    ESCOLHER_PIZZAS_NOVAMENTE, 
    ADICIONAR_PIZZAS, 
    INTENCAO_DESCONHECIDA
    
}