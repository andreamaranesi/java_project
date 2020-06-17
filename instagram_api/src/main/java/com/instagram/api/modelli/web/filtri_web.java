package com.instagram.api.modelli.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.config_generali.configurazione;
import com.instagram.api.config_generali.opzioni_filtri;
import com.instagram.api.eccezioni.access_token_errato;
import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.eccezioni.stringa_errata;
import com.instagram.api.strumenti_rapidi.strumenti_comuni;
import com.instagram.api.utenti.lista_utenti;
import com.instagram.api.modelli.chiamate_API;
import com.instagram.api.modelli.filtri;

/**
 * classe per restituire i post filtrati in formato html 
 * 
 * @see <a href="https://getmdl.io/">libreria Material Design Lite</a>
 * @see com.instagram.api.modelli.filtri
 * @author Andrea Maranesi
 *
 */
@Controller
class filtri_web extends chiamate_API {

	filtri filtri = new filtri();

	@RequestMapping(method = RequestMethod.GET, value = "/mostra_dati")
	public String ottieni_filtri(
			@RequestParam(value = "leggi_locale", defaultValue = "true") boolean leggi_dafile_locale,
			@RequestParam(value = "hashtag", defaultValue = "") String hashtag,
			@RequestParam(value = "data_caricamento", defaultValue = "") String data_caricamento, Model modello)
			throws JsonProcessingException, access_token_errato, eccezione, stringa_errata, FileNotFoundException {
		filtri.verifica_get_hashtag(hashtag);
		filtri.verifica_get_data_caricamento(data_caricamento);
		lista_utenti lista = new lista_utenti();
		if (leggi_dafile_locale) {
			String json = "";
			json = super.leggi(path_dati_lettura);
			try {
				lista = (lista_utenti) filtri.filtra_dati(new ObjectMapper().readValue(json, lista_utenti.class),
						config.opzioni, hashtag, data_caricamento, false);

			} catch (Exception e) {

				throw new eccezione(
						"Errore nel file locale" + path_dati_lettura + ". Verificarne l'esistenza o prova a fare una nuova chiamata");

			}
		} else {
			lista_utenti lista_utenti = nuova_chiamata_API();

			try {
				new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(path_dati_lettura),
						lista_utenti);
			} catch (IOException e) {

			}

			lista = (lista_utenti) filtri.filtra_dati(lista_utenti, config.opzioni, hashtag, data_caricamento, false);
		}
		modello.addAttribute("lista_utenti", lista);
		return "dati";

	}
}