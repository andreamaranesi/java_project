package com.instagram.api.config_generali;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.eccezioni.cifra_errata;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class opzioni_filtri {

	private int limite = 10;

	@JsonProperty("album")
	public opzioni_album opzioni_album = new opzioni_album();
	@JsonProperty("post")
	public opzioni_post opzioni_post = new opzioni_post();

	@JsonProperty("credenziali")
	public ArrayList<credenziali_utenti> credenziali_utenti = new ArrayList();

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) throws cifra_errata{
		if (this.limite >= 0 && this.limite<=50)
			this.limite = limite;
		else
			throw new cifra_errata("L'attributo limite per il numero dei post da filtrare",limite,0,50);
	}

}
