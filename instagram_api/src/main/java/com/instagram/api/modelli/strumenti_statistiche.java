package com.instagram.api.modelli;

import java.util.ArrayList;
import java.util.HashMap;

import com.instagram.api.config_generali.lunghezza_desc;
import com.instagram.api.config_generali.opzioni_statistiche;
import com.instagram.api.utenti.post;

public interface strumenti_statistiche {

	public String intervallo_caricamenti(ArrayList<post> posts);

	public void analizza_hashtag(post post, HashMap<String, Integer> hashtag_trovati);

	public void analizza_dimensioni_post(post post, opzioni_statistiche filtri, ArrayList<Integer> media_altezza,
			ArrayList<Integer> media_larghezza, ArrayList<Double> media_dimensioni);

}
