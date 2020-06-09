package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class opzioni_statistiche {

	private boolean hashtag = true;
	private boolean commenti = true;
	private int limite_post = 20;
	private boolean dimensione = true;
	private int tipo_dimensione = 0;
	private int numero_fasce = 4;

	public int getNumero_fasce() {
		return numero_fasce;
	}

	public void setNumero_fasce(int numero_fasce) {
		if (numero_fasce >= 2) {
			if (numero_fasce % 2 == 0)
				this.numero_fasce = numero_fasce;
			else
				this.numero_fasce=numero_fasce++;
		}
	}

	public int getTipo_dimensione() {
		return tipo_dimensione;
	}

	public void setTipo_dimensione(int tipo_dimensione) {
		if (this.tipo_dimensione >= 0 && this.tipo_dimensione <= 1)
			this.tipo_dimensione = tipo_dimensione;
	}

	public boolean isHashtag() {
		return hashtag;
	}

	public void setHashtag(boolean hashtag) {
		this.hashtag = hashtag;
	}

	public boolean isCommenti() {
		return commenti;
	}

	public void setCommenti(boolean commenti) {
		this.commenti = commenti;
	}

	public int getLimite_post() {
		return limite_post;
	}

	public void setLimite_post(int limite_post) {
		this.limite_post = limite_post;
	}

	public boolean isDimensione() {
		return dimensione;
	}

	public void setDimensione(boolean dimensione) {
		this.dimensione = dimensione;
	}

}
