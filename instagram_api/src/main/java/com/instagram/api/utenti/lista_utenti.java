package com.instagram.api.utenti;

import java.util.ArrayList;

public class lista_utenti {

	/**
	 * 
	 * 
	 */
	public ArrayList<utente> utenti=new ArrayList();
	
	/**
	 * Nel caso in cui venga passato come parametri di lista_utenti un valore booleano pari a true,
	 * la classe genera un nuovo {@link #utenti}, che nei suoi metodi getter andrà
	 * a specificare per ogni attributo tipo e descrizione (vedere {@link com.instagram.api.utenti.metadati.descrizione_attributo}
	 * @param metadati
	 */
	public lista_utenti(boolean metadati) {
		utenti.add(new utente(true));
	}

	public lista_utenti() {
	}
}
