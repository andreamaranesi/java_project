package com.instagram.api.config_generali;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.example1.demo.tools.Tools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class configurazione {

	public opzioni_filtri opzioni;
	public opzioni_statistiche statistiche;
	
	public configurazione(String path_opzioni, String path_statistiche) {
		
	}
}
