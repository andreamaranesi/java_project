package com.instagram.api.modelli;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.instagram.api.config_generali.configurazione;
import com.instagram.api.utenti.post;

public class statistiche extends chiamate_API implements strumenti_statistiche{

	
	
	@Autowired
	@Qualifier("config_bean")
	private configurazione config;
	
	
	/*
	 * 
	 * @author Andrea Maranesi
	 */
	@Override
	public String intervallo_caricamenti(ArrayList<post> posts) {
		return null;
	}

	
}
