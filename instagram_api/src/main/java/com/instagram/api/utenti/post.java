package com.instagram.api.utenti;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.config_generali.opzioni_filtri;
import com.instagram.api.strumenti_rapidi.shortcodes;
import com.instagram.api.utenti.metadati.descrizione_attributo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class post extends strumenti_post {

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

	String dimensione = null; // MB, KB
	private ArrayList<post> children = new ArrayList();

	public post(boolean metadati) {
		this.metadati = true;
	}

	public post() {

	}

	/**
	 * 
	 * @return IMMAGINE,VIDEO,CAROUSEL_ALBUM nel caso in cui il post sia stato
	 *         filtrato ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche}). <br>
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getTipo_post() {
		if (this.filtrato) {
			return ritorna_tipo_media(this.media_type);
		}
		if (this.metadati)
			return new descrizione_attributo("Stringa", "Il tipo del post (IMMAGINE, VIDEO, CAROUSEL_ALBUM)");
		return tipo_post;
	}

	/**
	 * 
	 * @return altezza in px nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri}) o usato da
	 *         {@link com.instagram.api.modelli.statistiche}). <br>
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getAltezza() {
		if (altezza == -1)
			return null;
		if (this.filtrato)
			return altezza + "px";
		if (this.metadati)
			return new descrizione_attributo("int", "l'altezza del media IMMAGINE in px");
		return altezza;
	}

	/**
	 * 
	 * @return larghezza in px nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri}) o usato da
	 *         {@link com.instagram.api.modelli.statistiche}). <br>
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getLarghezza() {
		if (larghezza == -1)
			return null;
		if (this.filtrato)
			return larghezza + "px";
		if (this.metadati)
			return new descrizione_attributo("int", "larghezza del media IMMAGINE in px");
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

	/**
	 * 
	 * @return Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getDimensione() {
		if (this.metadati)
			return new descrizione_attributo("String", "dimensione in MB o KB dell'immagine");
		return dimensione;
	}

	public HashMap<String, Integer> hashmap_data() {
		return super.manipola_data(this.timestamp);
	}

	/**
	 * @see #data_formattata(String)
	 * @return data creazione formattata nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche}). <br >
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getData_creazione() {
		if (this.filtrato)
			return data_formattata(this.timestamp);
		if (this.metadati && !this.album)
			return new descrizione_attributo("String", "data di caricamento del media");
		return data_creazione;
	}

	public void setData_creazione(String data_creazione) {
		this.data_creazione = data_creazione;
	}

	/**
	 * 
	 * @return attributo descrizione nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche}). <br>
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getDescrizione() {
		if (this.filtrato && this.restituisci_desc && this.restituisci_desc) {
			return this.caption;
		}
		if (this.metadati && !this.album)
			return new descrizione_attributo("String", "descrizione del post");
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		// this.descrizione = descrizione;
	}

	/**
	 * 
	 * @return Ritorna i figli dell'album. <br>
	 *         Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getChildren() {
		if (this.album) {
			return this.children;
		}
		if (this.metadati)
			return new descrizione_attributo("ArrayList<post>", "figli dell'album");
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

	/**
	 * 
	 * @return tutti gli hashtag presenti nella {@link #descrizione}
	 */
	public ArrayList<String> hashtag() {
		return super.hashtag(this.caption);
	}

	/**
	 * 
	 * @return Viene restituito un nuovo
	 *         {@link com.instagram.api.utenti.metadati.descrizione_attributo} nel
	 *         caso siano stati richiesti i metadati
	 */
	public Object getId() {
		if (this.metadati)
			return new descrizione_attributo("long", "id del post");
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return null nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche})
	 */
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

	/**
	 * 
	 * @return null nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche})

	 */
	public String getMedia_type() {
		if (!this.filtrato)
			return media_type;
		return null;
	}

	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}

	/**
	 * 
	 * @return null nel caso in cui il post sia stato filtrato
	 *         ({@link com.instagram.api.modelli.filtri} o usato da
	 *         {@link com.instagram.api.modelli.statistiche})
	 */
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
