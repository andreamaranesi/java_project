package com.instagram.api.eccezioni;

/**
 * la classe viene chiamata se in {@link com.instagram.api.modelli.chiamate_API#nuova_chiamata_API()} si verifica un errore durante la prima chiamata
 * per ottenere l'id e lo username dell'utente
 * 
 * @author Andrea Maranesi
 *
 */
public class access_token_errato extends Exception {

	public access_token_errato (String messaggio){
		super(messaggio);
	}
	
}
