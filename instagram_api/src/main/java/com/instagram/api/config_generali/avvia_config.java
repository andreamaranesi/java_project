package com.instagram.api.config_generali;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.strumenti_rapidi.shortcodes;

@Component
public class avvia_config {

	@Value("${opzioni_posts}")
	private String path_filtri;
	@Value("${statistiche}")
	private String path_statistiche;
	
	@Bean (name="config_bean")
	public configurazione nuovaConfigurazione() throws eccezione {
		return new configurazione(path_filtri,path_statistiche);
	}
}
