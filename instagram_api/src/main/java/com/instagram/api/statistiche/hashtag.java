package com.instagram.api.statistiche;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe per ottenere una statistica del numero di volte che i vari hashstag vengono utilizzati
 * 
 * @see com.instagram.api.utenti.strumenti_post#hashtag(String)
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class hashtag {
	public String nome;
	private int conteggio;
	
	public int getConteggio() {
		return conteggio;
	}
	public void setConteggio(int conteggio) {
		this.conteggio = conteggio;
	}
	
	
}
