package com.instagram.api.ricorsione_post;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contiene le specifiche di ogni media. Poichè {@link com.instagram.api.modelli.chiamate_API#iterazione_ottieni_media(com.instagram.api.utenti.post, String, String, int, long)} ritorna un numero limitato di post, è necessario effettuare un ciclo
 * utilizzando @param successivo, affinchè non restituisce una lista @param id_media nulla
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class media {

	@JsonProperty("data")
	ArrayList<id_media> id_media=new ArrayList();
	@JsonProperty("paging")
	paginazione paginazione;
	
	public ArrayList<id_media> getId_media() {
		return id_media;
	}
	public void setId_media(ArrayList<id_media> id_media) {
		this.id_media = id_media;
	}
	public paginazione getPaginazione() {
		return paginazione;
	}
	public void setPaginazione(paginazione paginazione) {
		this.paginazione = paginazione;
	}
	
	
	
}
