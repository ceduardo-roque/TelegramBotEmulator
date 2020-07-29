package com.fiap.bot.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fiap.bot.exceptions.CEPInvalidoException;

/**
 * Esta classe é utilizada para buscar informações de 
 * localização a partir de um CEP
 * 
 * @author Willian
 *
 */
public class LocalizacaoApi {
	/**
	 * Método de busca de Consulta do CEP
	 * @param codigoCEP informa o numero do cep
	 * @return localizacao retorna a local do cep informado na mensagem
	 * @throws Exception Exceção lançada caso ocorra algum problemea na consulta do CEP na API
	 */
	public Localizacao consultaCEP(String codigoCEP) throws Exception {
		Localizacao localizacao = new Localizacao();
		try {
			String codigoCEPFormatado = codigoCEP.replace("-", "");
			
			if (codigoCEPFormatado.length()==0)
				throw new CEPInvalidoException("CEP não informado corretamente.");
				
            URL url = new URL("https://viacep.com.br/ws/" + codigoCEPFormatado + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new CEPInvalidoException("Falha: Erro HTTP Codigo: " + conn.getResponseCode());
            }
            
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            
            String output = "";
            while ((output = br.readLine()) != null) { 
            	output = output.trim();
            	if (output.indexOf(":") > -1) {
            		String[] itens = output.split(":");
            		itens[1] = itens[1].substring(1, itens[1].length()-1).replaceAll("\"", ""); // Remove
            		
            		switch (itens[0]) {
	                	case "\"cep\"":
	                		localizacao.setCep(itens[1]);
	    					break;
	                	case "\"logradouro\"":
	                		localizacao.setLogradouro(itens[1]);
	    					break;
	                	case "\"bairro\"":
	                		localizacao.setBairro(itens[1]);
	    					break;
	                	case "\"localidade\"":
	                		localizacao.setCidade(itens[1]);
	    					break;
	                	case "\"uf\"":
	                		localizacao.setEstado(itens[1]);
	    					break;
	                	case "\"ibge\"":
	    					break;
	                	case "\"gia\"":
	                		break; 
	                	case "\"erro\"":
	                		throw new CEPInvalidoException("Endereço não encontrado para o CEP: " + codigoCEP);
	    				default:
	    					break;
    				}
            	}
           	            	            	
            }
            conn.disconnect();
            
        } catch (CEPInvalidoException e) {
            throw e;
        }
		
		return localizacao;
	}

}
