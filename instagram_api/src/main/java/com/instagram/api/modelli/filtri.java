package com.instagram.api.modelli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.config_generali.configurazione;
import com.instagram.api.config_generali.lunghezza_desc;
import com.instagram.api.config_generali.opzioni_filtri;
import com.instagram.api.eccezioni.access_token_errato;
import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.strumenti_rapidi.shortcodes;
import com.instagram.api.utenti.lista_utenti;
import com.instagram.api.utenti.post;
import com.instagram.api.utenti.utente;

public class filtri extends chiamate_API implements strumenti_filtri {


	@Autowired
	@Qualifier("config_bean")
	private configurazione config;

	
	@Override
	public boolean verifica_data(post post, String data_iniziale) {
		return false;
	}

	@Override
	public boolean verifica_hashtag(post post, String hashtag) {
		return false;
	}

	@Override
	public boolean verifica_descrizione(String descrizione, lunghezza_desc dimensioni) {
		return false;
	}
	
	private String filtra_dati(lista_utenti lista_utenti, opzioni_filtri filtri, String hashtag,
			String data_caricamento) {

		try {

			for (utente utente : lista_utenti.utenti) {

				ArrayList<post> post_filtrati = new ArrayList();
				int numero_post = 0;
				for (int i = 0; i < utente.posts.size() && numero_post < filtri.getLimite(); i++) {
					post post = utente.posts.get(i);
					post.filtrato = true;
					String descrizione = (String)post.getDescrizione();
					lunghezza_desc dimensioni = filtri.opzioni_post.lunghezza_desc;
					if (verifica_descrizione(descrizione, dimensioni) && verifica_hashtag(post, hashtag)
							&& verifica_data(post, data_caricamento)) {
						post.restituisci_desc = filtri.opzioni_post.descrizione;
						if (((String)post.getTipo_post()).contains("CAROUSEL_ALBUM")) {
							
						} else {
							boolean VIDEO = ((String)post.getTipo_post()).contains("VIDEO") ? true : false;

							
						}

					}

				}
				utente.posts = post_filtrati;

			}
			return new ObjectMapper().writeValueAsString(lista_utenti);

		} catch (Exception e) {
			shortcodes.pr(e.getLocalizedMessage());
		}
		return "{\"errore\":\"true\"}";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dati", produces = "application/json")
	@ResponseBody
	public Object ottieni_filtri(@RequestBody opzioni_filtri _filtri,
			@RequestParam(value = "leggi_locale", defaultValue = "false") boolean leggi_dafile_locale,
			@RequestParam(value = "hashtag", defaultValue = "") String hashtag,
			@RequestParam(value = "data_caricamento", defaultValue = "") String data_caricamento) throws JsonProcessingException, access_token_errato, eccezione {

		// leggi da il file ./dati-lettura.json
		if (leggi_dafile_locale) {
			String json = "";
			json = super.leggi("./dati_lettura.json");
			try {
				shortcodes.pr("ho appena letto " + json);
				return filtra_dati(new ObjectMapper().readValue(json, lista_utenti.class), _filtri, hashtag,
						data_caricamento);
			} catch (JsonMappingException e) {

			} catch (JsonProcessingException e) {

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
		return "errore";

	}
	
	

}
