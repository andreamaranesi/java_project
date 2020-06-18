package com.instagram.api.ricorsione_post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe contenente l'attributo <b>successivo</b>, da utilizzare nella ricorsione per ottenere tutti i post dell'utente
 * @see com.instagram.api.modelli.chiamate_API#ottieni_post(Object, String, long, boolean)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class cursori {

	@JsonProperty("after")
	private String successivo;

	public String getSuccessivo() {
		return successivo;
	}

	public void setSuccessivo(String successivo) {
		this.successivo = successivo;
	}

	
}
