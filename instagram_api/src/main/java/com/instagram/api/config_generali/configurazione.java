package com.instagram.api.config_generali;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.strumenti_rapidi.strumenti_comuni;
import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.strumenti_rapidi.shortcodes;


public class configurazione extends strumenti_comuni {

	public opzioni_filtri opzioni=new opzioni_filtri();

	/**
	 * 
	 * inizializza la classe {@link #opzioni} con i filtri forniti nel file config.json
	 * 
	 * @param path_opzioni
	 * @throws eccezione
	 */
	public configurazione(String path_opzioni) throws eccezione {
		String leggi_json_opzioni = leggi(path_opzioni);
		try {
			opzioni = new ObjectMapper().readValue(leggi_json_opzioni, opzioni_filtri.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public configurazione() {
	}
}
