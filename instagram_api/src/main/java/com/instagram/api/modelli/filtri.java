package com.instagram.api.modelli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.config_generali.*;
import com.instagram.api.eccezioni.access_token_errato;
import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.eccezioni.stringa_errata;
import com.instagram.api.ricorsione_post.id_media;
import com.instagram.api.ricorsione_post.media;
import com.instagram.api.strumenti_rapidi.shortcodes;
import com.instagram.api.utenti.lista_utenti;
import com.instagram.api.utenti.post;
import com.instagram.api.utenti.utente;

@Controller
public class filtri extends chiamate_API implements strumenti_filtri {

	@Autowired
	@Qualifier("config_bean")
	private configurazione config;

	public boolean analizza_dimensioni(post post, opzioni_filtri filtri) {
		boolean VIDEO = ((String) post.getTipo_post()).contains("VIDEO") ? true : false;
		int tipo_dimensioni = filtri.opzioni_post.getTipo_dati();
		dimensioni_media dimensioni = filtri.opzioni_post.dimensioni_media;
		double min = dimensioni.getMin();
		double max = dimensioni.getMax();
		String media_url = post.getMedia_url();
		boolean rest_dimensioni = filtri.opzioni_post.analisi_media;
		try {

			HashMap<String, Object> getDimensioni = post.getDimensioni(media_url, VIDEO);
			int altezza = (int) getDimensioni.get("altezza");

			int larghezza = (int) getDimensioni.get("larghezza");

			double dimensione = 0.0;
			try {
				dimensione = Double.valueOf((Long) getDimensioni.get("dimensione"));
			} catch (Exception e) {
				shortcodes.pr(e.getLocalizedMessage());
			}
			switch (tipo_dimensioni) {
			case 0:
				dimensione = (double) (dimensione * Math.pow(10, -6));
				break;
			case 1:
				dimensione = (double) (dimensione * Math.pow(10, -3));
				break;
			}

			if (dimensione >= min && dimensione <= max) {
				if (rest_dimensioni) {
					DecimalFormat formatta_dimensione = new DecimalFormat("#.###");
					String dim = formatta_dimensione.format(dimensione); 
					dim += tipo_dimensioni == 0 ? " MB" : tipo_dimensioni == 1 ? " KB" : "--";
					post.setAltezza(altezza);
					post.setLarghezza(larghezza);
					post.setDimensione(dim);
				}
				if (!VIDEO) {
					dimensioni_px px = filtri.opzioni_post.dimensioni_px;
					if (altezza >= px.altezza.getMin() && altezza <= px.altezza.getMax()) {
						if (larghezza >= px.larghezza.getMin() && larghezza <= px.larghezza.getMax()) {
							return true;
						}
					}
					return false;
				}

				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private String filtra_dati(lista_utenti lista_utenti, opzioni_filtri filtri, String hashtag,
			String data_caricamento) throws eccezione {

		try {

			for (utente utente : lista_utenti.utenti) {

				ArrayList<post> post_filtrati = new ArrayList();
				int numero_post = 0;
				for (int i = 0; i < utente.posts.size() && numero_post < filtri.getLimite(); i++) {
					post post = utente.posts.get(i);
					post.filtrato = true;
					String descrizione = (String) post.getDescrizione();
					lunghezza_desc dimensioni = filtri.opzioni_post.lunghezza_desc;
					if (verifica_descrizione(descrizione, dimensioni) && verifica_hashtag(post, hashtag)
							&& verifica_data(post, data_caricamento)) {
						post.restituisci_desc = filtri.opzioni_post.descrizione;
						if (((String) post.getTipo_post()).contains("CAROUSEL_ALBUM")) {
							ArrayList<post> post_album_filtrati = new ArrayList();
							post.setAlbum(true);
							int numero_post_album = 0;

							ArrayList<post> figli = ((ArrayList<post>) post.getChildren());
							for (int j = 0; j < figli.size()
									&& numero_post_album < filtri.opzioni_album.getLimite(); j++) {
								post post_album = figli.get(j);
								post_album.filtrato = true;
								if (analizza_dimensioni(post_album, filtri)) {
									post_album_filtrati.add(post_album);
									numero_post_album++;
								}

							}

							post_filtrati.add(post);
							post.setChildren(post_album_filtrati);
							post.setAlbum(true);
							// post_filtrati.add(post);
						} else {
							boolean VIDEO = ((String) post.getTipo_post()).contains("VIDEO") ? true : false;

							if (analizza_dimensioni(post, filtri)) {

								numero_post++;
								post_filtrati.add(post);

							}
						}

					}

				}
				utente.posts = post_filtrati;

			}
			return new ObjectMapper().writeValueAsString(lista_utenti);

		} catch (Exception e) {
			throw new eccezione("Si è verificato un errore: " + e.getLocalizedMessage());
		}
	}

	@Override
	public boolean verifica_hashtag(post post, String hashtag) {
		if (hashtag.isEmpty())
			return true;
		if (post.getDescrizione() == null)
			return false;
		ArrayList<String> _hashtag = post.hashtag();
		String separatore = "||";
		if (_hashtag == null)
			return true;
		hashtag = hashtag.trim();
		ArrayList<String> hashtag_da_cercare = new ArrayList();
		hashtag_da_cercare = cerca_valori(hashtag, separatore); //da finire e rivedere cerca_valori

		for (String singolo_hashtag : hashtag_da_cercare) {
			if (_hashtag.contains(singolo_hashtag))
				return true;
		}

		return false;
	}

	@Override
	public boolean verifica_descrizione(String descrizione, lunghezza_desc dimensioni) {
		if (descrizione == null) {
			if (dimensioni.getMin() == 0)
				return true;
			return false;
		} else {
			if (descrizione.length() >= dimensioni.getMin() && descrizione.length() <= dimensioni.getMax())
				return true;
			return false;
		}

	}

	
	protected void verifica_get_hashtag(String hashtag_richiesti) throws stringa_errata {
		if (verifica_regex("[#]", hashtag_richiesti))
			throw new stringa_errata("hashtag=" + hashtag_richiesti + " non può contenere il carattere #");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dati", produces = "application/json")
	@ResponseBody
	public Object ottieni_filtri(@RequestBody opzioni_filtri _filtri,
			@RequestParam(value = "leggi_locale", defaultValue = "false") boolean leggi_dafile_locale,
			@RequestParam(value = "hashtag", defaultValue = "") String hashtag,
			@RequestParam(value = "data_caricamento", defaultValue = "") String data_caricamento)
			throws JsonProcessingException, access_token_errato, eccezione, stringa_errata {
		verifica_get_hashtag(hashtag);
		verifica_get_data_caricamento(data_caricamento);

		// leggi da il file ./dati-lettura.json
		if (leggi_dafile_locale) {
			String json = "";
			json = super.leggi("./dati_lettura.json");
			try {
				shortcodes.pr("ho appena letto " + json);
				return filtra_dati(new ObjectMapper().readValue(json, lista_utenti.class), _filtri, hashtag,
						data_caricamento);

			} catch (Exception e) {				

				throw new eccezione("Errore nel file locale dati_lettura.json. Verificarne l'esistenza o prova a fare una nuova chiamata");
				
			} 
		} else {
			// leggi da la variabile locale config.json
			lista_utenti lista_utenti = nuova_chiamata_API();

			try {
				new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("./dati_lettura.json"),
						lista_utenti);
			} catch (IOException e) {

			}

			return filtra_dati(lista_utenti, _filtri, hashtag, data_caricamento);
		}

	}

	@Override
	public boolean verifica_data(post post, String data_iniziale) {
		return false;
	}

}
