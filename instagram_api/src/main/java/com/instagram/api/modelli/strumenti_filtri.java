package com.instagram.api.modelli;

import com.instagram.api.config_generali.lunghezza_desc;
import com.instagram.api.utenti.post;

public interface strumenti_filtri {

	public boolean verifica_data(post post, String data_iniziale);
	public boolean verifica_hashtag(post post, String hashtag);
	public boolean verifica_descrizione(String descrizione, lunghezza_desc dimensioni);
	
}
