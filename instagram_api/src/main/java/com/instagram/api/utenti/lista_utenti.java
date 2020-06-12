package com.instagram.api.utenti;

import java.util.ArrayList;

public class lista_utenti {

	public ArrayList<utente> utenti=new ArrayList();
	
	public lista_utenti(boolean metadati) {
		utenti.add(new utente(true));
	}

	public lista_utenti() {
	}
}
