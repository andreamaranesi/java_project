package com.instagram.api.statistiche;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe per suddividere, in fasce, la dimensione dei post presenti nei vari utenti
 * @see com.instagram.api.modelli.statistiche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class dati_media {

	private int conteggio;
	@JsonIgnore
	public int tipo_dati=0;
	private String min,max;


	public String getMin() {
		String dato= tipo_dati==0? " MB" : " KB";
		return min + dato;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		String dato= tipo_dati==0? " MB" : " KB";
		return max + dato;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public int getConteggio() {
		return conteggio;
	}

	public void setConteggio(int conteggio) {
		this.conteggio = conteggio;
	}


	
	
	
	
}
