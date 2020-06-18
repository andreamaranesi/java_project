package com.instagram.api.statistiche;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe per definire le statistiche per ogni
 * {@link com.instagram.api.utenti.utente}
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class dati_statistiche {

	private long id;
	private String username;
	private String dimensione_media;
	private String altezza_media, larghezza_media;
	private String media_caricamenti;
	@JsonProperty("lunghezza_media_descrizione")
	private String media_lung_descrizione;

	public ArrayList<dati_media> dati_media = new ArrayList();
	public ArrayList<hashtag> hashtag = new ArrayList();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAltezza_media() {
		return altezza_media;
	}

	public void setAltezza_media(String altezza_media) {
		if (!altezza_media.contains("-1"))
			this.altezza_media = altezza_media;
	}

	public String getLarghezza_media() {

		return larghezza_media;
	}

	public void setLarghezza_media(String larghezza_media) {
		if (!larghezza_media.contains("-1"))
			this.larghezza_media = larghezza_media;
	}

	public String getMedia_caricamenti() {
		return media_caricamenti;
	}

	public void setMedia_caricamenti(String media_caricamenti) {
		this.media_caricamenti = media_caricamenti;
	}

	public String getMedia_lung_descrizione() {
		return media_lung_descrizione;
	}

	public void setMedia_lung_descrizione(String media_lung_descrizione) {
		if (!media_lung_descrizione.contains("-1"))
			this.media_lung_descrizione = media_lung_descrizione;
	}

	public String getDimensione_media() {

		return dimensione_media;
	}

	public void setDimensione_media(String dimensione_media) {
		if (!dimensione_media.contains("-1"))
			this.dimensione_media = dimensione_media;
	}

}
