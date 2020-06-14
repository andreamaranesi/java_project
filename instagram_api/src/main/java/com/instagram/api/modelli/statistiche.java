package com.instagram.api.modelli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
import com.instagram.api.eccezioni.stringa_errata;
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

/**
 * la classe seguente serve per generare delle statistiche sui post di ciascun
 * {@link com.instagram.api.utenti.utente} ottenuti tramite
 * {@link #nuova_chiamata_API()}
 * 
 */
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
			int c = 0;
			for (int i = 0; i < dati.size(); i += 2) {
				if (i + 1 < dati.size()) {
					somma += (dati.get(i) - dati.get(i + 1));
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

	/**
	 * ottiene dimensioni (in MB o KB) del post; l'altezza e la larghezza in px nel
	 * caso in cui il post sia un'immagine. Aggiunge alle liste dinamiche
	 * media_altezza, media_larghezza e media_dimensioni i valori così ottenuti.
	 * 
	 */
	@Override
	public void analizza_dimensioni_post(post post, opzioni_statistiche filtri, ArrayList<Integer> media_altezza,
			ArrayList<Integer> media_larghezza, ArrayList<Double> media_dimensioni) {
		boolean VIDEO = ((String) post.getTipo_post()).contains("VIDEO") ? true : false;
		int tipo_dimensioni = filtri.getTipo_dimensione();

		HashMap<String, Object> getDimensioni = post.getDimensioni(post.getMedia_url(), VIDEO);
		int altezza = (int) getDimensioni.get("altezza");
		int larghezza = (int) getDimensioni.get("larghezza");

		if (altezza != -1) {
			media_altezza.add(altezza);
			media_larghezza.add(larghezza);
		}
		double dimensione = Double.valueOf((Long) getDimensioni.get("dimensione"));
		switch (tipo_dimensioni) {
		case 0:
		default:
			dimensione *= Math.pow(10, -6);
			break;
		case 1:
			dimensione *= Math.pow(10, -3);
			break;
		}
		media_dimensioni.add(dimensione);
	}

	/**
	 * Cerca gli hashtag presenti nel post e li inserisce all'interno
	 * dell'hashmap @param hashtag_trovati Se @param hashtag_trovati contiene
	 * l'hashtag, allora aumenta di 1 il valore, altrimenti aggiunge una nuova
	 * coppia chiave-valore (hashtag, 1)
	 * 
	 * @see #analizza_hashtag(post, HashMap)
	 */
	@Override
	public void analizza_hashtag(post post, HashMap<String, Integer> hashtag_trovati) {

		ArrayList<String> hashtag_post = post.hashtag();
		for (String hashtag : hashtag_post) {
			if (hashtag_trovati.containsKey(hashtag))
				hashtag_trovati.compute(hashtag, (key, conteggio) -> conteggio + 1);
			else
				hashtag_trovati.put(hashtag, 1);
		}
	}

	/**
	 * per ogni utente memorizza le dimensioni in MB o KB dei post, l'eventuale
	 * altezza e larghezza in px, un conteggio degli hashtag, per tutti i post che
	 * rispettano l'arco temporale scelto. Infine crea da questi dati le statistiche
	 * (media dimensioni, media altezza, ecc..)
	 * 
	 * 
	 */
	public String genera_statistiche(lista_utenti lista_utenti, opzioni_statistiche filtri, String data_caricamento) {

		lista_statistiche lista = new lista_statistiche();
		try {

			for (utente utente : lista_utenti.utenti) {

				ArrayList<Integer> media_descrizione = new ArrayList();
				ArrayList<Integer> media_altezza = new ArrayList();
				ArrayList<Integer> media_larghezza = new ArrayList();
				ArrayList<Double> media_dimensioni = new ArrayList();
				HashMap<String, Integer> hashtag_trovati = new HashMap();

				ArrayList<post> posts = new ArrayList();
				dati_statistiche dati = new dati_statistiche();
				lista.dati_statistiche.add(dati);
				dati.setId((long) utente.getId());
				dati.setUsername((String) utente.getUsername());
				int contatore=0;
				for (int i = 0; i < utente.posts.size() && contatore < filtri.getLimite_post(); i++) {
					post post = utente.posts.get(i);
					post.filtrato = true;
					String descrizione = (String) post.getDescrizione();

					if (verifica_data(post, data_caricamento)) {
						contatore++;
						posts.add(post);
						if (descrizione == null)
							media_descrizione.add(0);
						else {
							if (filtri.isHashtag())
								analizza_hashtag(post, hashtag_trovati);

							media_descrizione.add(descrizione.length());
						}
						if (filtri.isDimensione()) {
							if (((String) post.getTipo_post()).contains("CAROUSEL_ALBUM")) {
								post.setAlbum(true);
								int numero_post_album = 0;

								ArrayList<post> figli = ((ArrayList<post>) post.getChildren());
								for (int j = 0; j < figli.size(); j++) {
									post post_album = figli.get(j);
									post_album.filtrato = true;
									analizza_dimensioni_post(post_album, filtri, media_altezza, media_larghezza,
											media_dimensioni);

								}
							} else {
								analizza_dimensioni_post(post, filtri, media_altezza, media_larghezza,
										media_dimensioni);
							}
						}

					}

				}
				String descrizione = genera_media_int(media_descrizione) + " caratteri";
				dati.setMedia_lung_descrizione(descrizione);
				
				if (filtri.isDimensione()) {
					String altezza = genera_media_int(media_altezza) + "px";
					String larghezza = genera_media_int(media_larghezza) + "px";
					DecimalFormat formatta_dimensione = new DecimalFormat("#.###");

					dati.setMedia_caricamenti(intervallo_caricamenti(posts));

					if (media_dimensioni.size() >= 2) {
						DecimalFormat formatter = formatta_dimensione;
						int numero_fasce = filtri.getNumero_fasce();
						double min = Collections.min(media_dimensioni);
						double max = Collections.max(media_dimensioni);

						double media = (max - min);

						int c = 0;
						do {
							double fascia = media / numero_fasce;
							double massimo_sinistro = fascia + min;
							double minimo = min;
							List<Double> trova_fascia_1 = media_dimensioni.stream()
									.filter(valore -> valore >= minimo && valore < massimo_sinistro)
									.collect(Collectors.toList());
							dati_media dati_media1 = new dati_media();
							dati_media1.tipo_dati = filtri.getTipo_dimensione();
							dati_media1.setMin(formatter.format(minimo));
							dati_media1.setMax(formatter.format(massimo_sinistro));
							dati_media1.setConteggio(trova_fascia_1.size());
							dati.dati_media.add(dati_media1);
							double massimo_destro = massimo_sinistro + fascia;
							List<Double> trova_fascia_2 = media_dimensioni.stream()
									.filter(valore -> valore >= massimo_sinistro && valore <= massimo_destro)
									.collect(Collectors.toList());
							dati_media dati_media2 = new dati_media();
							dati_media2.tipo_dati = filtri.getTipo_dimensione();
							dati_media2.setMin(formatter.format(massimo_sinistro));
							dati_media2.setMax(formatter.format(massimo_destro));
							dati_media2.setConteggio(trova_fascia_2.size());
							dati.dati_media.add(dati_media2);

							min += 2 * fascia;
							c += 2;
						} while (c < numero_fasce);
					}

					double dimensioni = genera_media_double(media_dimensioni); // 0.00330
					String dimensione = formatta_dimensione.format(dimensioni); // 0,003
					switch (filtri.getTipo_dimensione()) {
					case 0:
					default:
						dimensione += " MB"; // 0,003 MB
						break;
					case 1:
						dimensione += " KB"; // 0,003 KB
						break;
					}

					dati.setDimensione_media(dimensione);
					dati.setAltezza_media(altezza);
					dati.setLarghezza_media(larghezza);
				}
				for (Map.Entry<String, Integer> valore : hashtag_trovati.entrySet()) {
					hashtag hashtag = new hashtag();
					hashtag.nome = valore.getKey();
					hashtag.setConteggio(valore.getValue());
					dati.hashtag.add(hashtag);
				}

			}
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(lista);

		} catch (Exception e) {
			shortcodes.pr(e.getLocalizedMessage());
		}
		return "{\"errore\":\"true\"}";
	}

	/**
	 * 
	 * legge dal file locale dati_lettura.json i dati ottenuti in precedenza
	 * mediante una nuova chiamata API (@see {@link #nuova_chiamata_API()}), oppure
	 * effettua una nuova chiamata. In quest'ultimo caso memorizza nel file locale
	 * dati_lettura.json i dati così ottenuti.
	 * 
	 * Mediante il metodo @see
	 * {@link #genera_statistiche(lista_utenti, opzioni_statistiche, String)}
	 * restituisce un JSON con tutte le statistiche ottenute dai vari post
	 * 
	 * @param filtri
	 * @param leggi_dafile_locale
	 * @param data_caricamento
	 * @return JSON delle statistiche
	 * @throws JsonProcessingException
	 * @throws access_token_errato
	 * @throws eccezione
	 * @throws FileNotFoundException 
	 * @throws stringa_errata 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/statistiche", produces = "application/json")
	@ResponseBody
	public Object ottieni_statistiche(@RequestBody opzioni_statistiche filtri,
			@RequestParam(value = "leggi_locale", defaultValue = "true") boolean leggi_dafile_locale,
			@RequestParam(value = "data_caricamento", defaultValue = "") String data_caricamento)
			throws JsonProcessingException, access_token_errato, eccezione, FileNotFoundException, stringa_errata {

		verifica_get_data_caricamento(data_caricamento);
		// leggi da il file ./dati-lettura.json
		if (leggi_dafile_locale) {
			String json = "";
			json = super.leggi("./dati_lettura.json");
			try {
				return genera_statistiche(new ObjectMapper().readValue(json, lista_utenti.class), filtri,
						data_caricamento);
			} catch (Exception e) {
				throw new eccezione(
						"Errore nel file locale dati_lettura.json. Verificarne l'esistenza o prova a fare una nuova chiamata");
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
		ArrayList<Long> long_date_caricamento = new ArrayList();

		for (post post : posts) {
			HashMap<String, Integer> data_post = post.hashmap_data();
			Calendar data = Calendar.getInstance();
			data.set(data_post.get("anno"), data_post.get("mese"), data_post.get("giorno"));
			long_date_caricamento.add(data.getTimeInMillis() / 1000);
		}

		long media = genera_media_caricamento_post(long_date_caricamento);
		if (media != -1) {
			long media_giorni = media / (60 * 60 * 24);
			if (media_giorni == 0)
				return "ogni giorno";
			else if (media_giorni < 14)
				return "ogni settimana";
			else if (media_giorni < 30)
				return "ogni " + media_giorni / 7 + " settimane";
			else if (media_giorni > 27 && media_giorni < 31)
				return "ogni mese";
			else
				return "ogni " + media_giorni / 30 + " mesi";
		}
		return null;
	}

}
