package com.instagram.api.config_generali;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * legge nel costruttore i file locali json contenenti i filtri e le statistiche di default, nonché
 * gli access_token dei vari utenti
 * 
 * @author Alessio Pettinari
 *
 */
public class configurazione extends leggi_file{

	public opzioni_filtri opzioni;
	public opzioni_statistiche statistiche;
	
		public configurazione (String path_opzioni, String path_statistiche) {
			String leggi_json_opzioni=leggi(path_opzioni);
			String leggi_json_statistiche=leggi(path_statistiche);
			try {
				opzioni=new ObjectMapper().readValue(leggi_json_opzioni, opzioni_filtri.class);
				statistiche=new ObjectMapper().readValue(leggi_json_statistiche, opzioni_statistiche.class);			
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		public configurazione() {
		}
}
