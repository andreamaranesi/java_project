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
	public opzioni_statistiche statistiche;

	public configurazione(String path_opzioni, String path_statistiche) throws eccezione {
		String leggi_json_opzioni = leggi(path_opzioni);
		String leggi_json_statistiche = leggi(path_statistiche);
		try {
			opzioni = new ObjectMapper().readValue(leggi_json_opzioni, opzioni_filtri.class);
			statistiche = new ObjectMapper().readValue(leggi_json_statistiche, opzioni_statistiche.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public configurazione() {
	}
}
