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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.instagram.api.ricorsione_post.id_media;
import com.instagram.api.ricorsione_post.media;
import com.instagram.api.statistiche.dati_media;
import com.instagram.api.statistiche.dati_statistiche;
import com.instagram.api.statistiche.hashtag;
import com.instagram.api.statistiche.lista_statistiche;
import com.instagram.api.strumenti_rapidi.shortcodes;
import com.instagram.api.utenti.lista_utenti;
import com.instagram.api.utenti.post;
import com.instagram.api.utenti.utente;

@Controller
public class statistiche extends chiamate_API implements strumenti_statistiche {

	
	private long genera_media_long(ArrayList<Long> dati) {

		if (dati.size() > 0) {
			long somma = 0;
			for (Long dato : dati) {
				somma += dato;
			}
			return somma / dati.size();
		}
		return -1;
	}

	private long genera_media_caricamento_post(ArrayList<Long> dati) {

		if (dati.size() >= 2) {
			long somma = 0;
			int c=0;
			for(int i=0;i<dati.size();i+=2) {
				if(i+1<dati.size()) {
					somma+=(dati.get(i) - dati.get(i+1));
					c++;
				}
			}
			return somma / c;
		}
		return -1;
	}
	private int genera_media_int(ArrayList<Integer> dati) {

		if (dati.size() > 0) {
			int somma = 0;
			for (int dato : dati) {
				somma += dato;
			}
			return somma / dati.size();
		}
		return -1;
	}

	private double genera_media_double(ArrayList<Double> dati) {
		if (dati.size() > 0) {
			double somma = 0;
			for (double dato : dati) {
				somma += dato;
			}
			return somma / dati.size();
		}
		return -1;
	}

	@Override
	public void analizza_dimensioni_post(post post, opzioni_statistiche filtri, ArrayList<Integer> media_altezza,
			ArrayList<Integer> media_larghezza, ArrayList<Double> media_dimensioni) {
		
	}

	@Override
	public void analizza_hashtag(post post, HashMap<String, Integer> hashtag_trovati) {

		
	}


	private String genera_statistiche(lista_utenti lista_utenti, opzioni_statistiche filtri, String data_caricamento) {

		lista_statistiche lista = new lista_statistiche();
		try {

			for (utente utente : lista_utenti.utenti) {

				
				
			}
			// String ciao=new ObjectMapper().writeValueAsString(lista_utenti);

			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(lista);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			shortcodes.pr(e.getLocalizedMessage());
		}
		return "{\"errore\":\"true\"}";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/statistiche", produces = "application/json")
	@ResponseBody
	public Object ottieni_statistiche(@RequestBody opzioni_statistiche filtri,
			@RequestParam(value = "leggi_locale", defaultValue = "true") boolean leggi_dafile_locale,
			@RequestParam(value = "data_caricamento", defaultValue = "") String data_caricamento) throws JsonProcessingException, access_token_errato, eccezione {

		// leggi da il file ./dati-lettura.json
		if (leggi_dafile_locale) {
			String json = "";
			json = super.leggi("./dati_lettura.json");
			try {
				return genera_statistiche(new ObjectMapper().readValue(json, lista_utenti.class), filtri,
						data_caricamento);
			} catch (Exception e) {
				throw new eccezione("Errore nel file locale dati_lettura.json. Verificarne l'esistenza o prova a fare una nuova chiamata");
			}

		} else {
			// leggi da la variabile locale config.json
			lista_utenti lista_utenti = nuova_chiamata_API();

			try {
				new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(path_dati_lettura),
						lista_utenti);
			} catch (IOException e) {

			}
			return genera_statistiche(lista_utenti, filtri, data_caricamento);
		}
		
	}

	@Override
	public String intervallo_caricamenti(ArrayList<post> posts) {
	
	}
	

}
