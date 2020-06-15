package com.instagram.api.utenti.metadati;

/**
 * classe ausiliaria alla generazione dei metadati
 * @see com.instagram.api.modelli.metadati#ottieni_metadati()
 *
 */
public class descrizione_attributo {

	public String tipo;
	public String descrizione;
	
	public descrizione_attributo(String tipo, String descrizione) {
		this.tipo = tipo;
		this.descrizione = descrizione;
	}
	
	
}
