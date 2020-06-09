package com.instagram.api.utenti;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.config_generali.opzioni_filtri;
import com.instagram.api.strumenti_rapidi.shortcodes;

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
	public String getTipo_post() {
		if (this.filtrato) {
			return ritorna_tipo_media(this.media_type);
		}
		return tipo_post;
	}


	public Object getAltezza() {
		if (altezza == -1)
			return null;
		if (this.filtrato)
			return altezza + "px";
		return altezza;
	}
	

	public Object getLarghezza() {
		if (larghezza == -1)
			return null;
		if(this.filtrato)
			return larghezza + "px";
		return larghezza;
	}

	public void setAltezza(int altezza) {
		if (this.filtrato)
			this.altezza = altezza;
	}

	public void setLarghezza(int larghezza) {
		if (this.filtrato)
			this.larghezza = larghezza;
	}

	public void setDimensione(String dimensione) {
		if (this.filtrato)
			this.dimensione = dimensione;
	}

	public String getDimensione() {
		return dimensione;
	}


	public HashMap<String,Integer> hashmap_data() {
		return super.manipola_data(this.timestamp);
	}
	
	public String getData_creazione() {
		if (this.filtrato)
			return data_formattata(this.timestamp);
		return data_creazione;
	}

	public void setData_creazione(String data_creazione) {
		this.data_creazione = data_creazione;
	}

	public String getDescrizione() {
		if (this.filtrato && this.restituisci_desc && this.restituisci_desc) {
			return this.caption;
		}
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		// this.descrizione = descrizione;
	}

	

	public ArrayList<post> getChildren() {
		if(this.album) {
			return this.children;
		}
		return null;
	}

	public void setChildren(ArrayList<post> children) {
		this.children = children;
	}

	public boolean isAlbum() {
		return album;
	}

	public void setAlbum(boolean album) {
		this.album = album;
	}

	public ArrayList<String> hashtag() {
		return super.hashtag(this.caption);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCaption() {
		if (this.filtrato)
			return null;
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMedia_url() {
		return media_url;
	}

	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}

	public String getMedia_type() {
		if (!this.filtrato)
			return media_type;
		return null;
	}

	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}

	public String getTimestamp() {
		if (this.filtrato) {
			return null;
		}
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
