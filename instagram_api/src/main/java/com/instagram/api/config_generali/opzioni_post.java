package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.eccezioni.eccezione;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class opzioni_post {
	
	private int tipo_dati = 0;
	public boolean descrizione = true;	
	public boolean analisi_media = true;

	public int getTipo_dati() {
		return tipo_dati;
	}

	public void setTipo_dati(int tipo_dati) {
		if (tipo_dati >= 0 && tipo_dati <= 1)
			this.tipo_dati = tipo_dati;

	}

	public boolean isDescrizione() {
		return descrizione;
	}

	public void setDescrizione(boolean descrizione) {
		this.descrizione = descrizione;
	}

}
