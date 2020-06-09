package com.instagram.api.modelli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.config_generali.*;
import com.instagram.api.ricorsione_post.id_media;
import com.instagram.api.ricorsione_post.media;
import com.instagram.api.strumenti_rapidi.strumenti_comuni;
import com.instagram.api.strumenti_rapidi.shortcodes;
import com.instagram.api.utenti.lista_utenti;
import com.instagram.api.utenti.post;
import com.instagram.api.utenti.utente;
@Controller
public abstract class chiamate_API extends strumenti_comuni{

	@Autowired
	@Qualifier("config_bean")
	private configurazione config;

	String ottieni_utente = "https://graph.instagram.com/me?access_token={token}&fields=id,username";
	String ottieni_media = "https://graph.instagram.com/{user-id}/media?access_token={token}&limit={limite}&after={after}";

	String ottieni_album_media = "https://graph.instagram.com/{media-id}/children?access_token={access-token}&limit={limite}&after={after}";

	String ottieni_post = "https://graph.instagram.com/{media-id}?fields=media_url,caption,media_type,timestamp&access_token={token}";

	String ottieni_html_post = "https://api.instagram.com/oembed?url=https://www.instagram.com/p/{media-id}";
	String ottieni_post_album = "https://graph.instagram.com/{media-id}?fields=media_url,media_type&access_token={token}";

	
	private void ottieni_post(Object user_album, String access_token, long id, boolean album) {
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(album ? ottieni_post_album : ottieni_post, String.class, id,
				access_token);
		try {
			post post = new ObjectMapper().readValue(json, post.class);
			utente utente;
			if (album) {
				post _album = (post) user_album;
				_album.getChildren().add(post);
			} else {
				utente = (utente) user_album;
				utente.posts.add(post);
			}
			if (!album && post.getMedia_type().contains("CAROUSEL_ALBUM")) {
				post.setAlbum(true);
				iterazione_ottieni_media(post, access_token, "", config.opzioni.opzioni_album.getLimite(), post.getId());
			}

		} catch (JsonProcessingException e) {
			shortcodes.pr("errore");
		}
	}

	private void iterazione_ottieni_media(post album, String access_token, String after, int rimanenti, long media_id) {
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(ottieni_album_media, String.class, media_id, access_token, rimanenti,
				after);
		media media;
		try {
			media = new ObjectMapper().readValue(json, media.class);
			if (media.getId_media() != null) {
				rimanenti -= media.getId_media().size();
				for (id_media data : media.getId_media()) {

					ottieni_post(album, access_token, data.getId(), true);

				}
				if (rimanenti > 0 && media.getPaginazione() != null) {
					iterazione_ottieni_media(album, access_token, media.getPaginazione().getCursori().getSuccessivo(), rimanenti,
							media_id);
				}
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	

	private void iterazione_ottieni_media(utente user, String access_token, String after, int rimanenti) {
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(ottieni_media, String.class, user.getId(), access_token, rimanenti,
				after);
		media media;
		try {
			media = new ObjectMapper().readValue(json, media.class);
			if (media.getId_media() != null) {
				rimanenti = rimanenti - media.getId_media().size();
				

				for (int i = 0; i < media.getId_media().size(); i++) {
					id_media data = media.getId_media().get(i);
					ottieni_post(user, access_token, data.getId(), false);
				}

				if (rimanenti > 0 && media.getPaginazione() != null) {
					iterazione_ottieni_media(user, access_token, media.getPaginazione().getCursori().getSuccessivo(), rimanenti);
				}
			}

		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
	
	}





	public lista_utenti nuova_chiamata_API() {
		lista_utenti lista_utenti = new lista_utenti();

		for (credenziali_utenti utente : config.opzioni.credenziali_utenti) {
			shortcodes.pr(utente.getAccess_token());

			String access_token = utente.getAccess_token();
			RestTemplate restTemplate = new RestTemplate();

			try {
				String json = restTemplate.getForObject(ottieni_utente, String.class, access_token);
				utente user = new ObjectMapper().readValue(json, utente.class);
				lista_utenti.utenti.add(user);
				
				iterazione_ottieni_media(user, access_token, "", config.opzioni.getLimite());
			} catch (Exception e) {

			}

		}

		return lista_utenti;
	}
}
