package com.instagram.api.statistiche;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class lista_statistiche {

	@JsonProperty("utenti")
	public ArrayList<dati_statistiche> dati_statistiche=new ArrayList();
	

}
