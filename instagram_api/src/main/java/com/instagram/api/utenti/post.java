package com.instagram.api.utenti;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class post extends strumenti_post{

	long id;
	String caption, media_url, media_type, timestamp;
	@JsonIgnore
	public boolean album = false;

	@JsonIgnore
	public boolean filtrato = false;

	@JsonIgnore
	public boolean metadati = false;
	@JsonIgnore
	public boolean restituisci_desc = true;
	@JsonIgnore
	public boolean restituisci_dimensioni = true;

	
	String descrizione = null;
	String tipo_post = null;
	String data_creazione = null;

	int altezza = -1;
	int larghezza = -1;
	String dimensione=null; // MB, KB
	private ArrayList<post> children = new ArrayList();
	
}
