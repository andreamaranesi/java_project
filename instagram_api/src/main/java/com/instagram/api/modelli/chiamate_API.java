package com.instagram.api.modelli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

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


	private void iterazione_ottieni_media(utente user, String access_token, String after, int rimanenti) {
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(ottieni_media, String.class, user.getId(), access_token, rimanenti,
				after);
		media media;
		try {
			media = new ObjectMapper().readValue(json, media.class);
			if (media.getId_media() != null) {
				rimanenti = rimanenti - media.getId_media().size();
				// Tools.pr(media.getData().size());

				for (int i = 0; i < media.getId_media().size(); i++) {
                    //creare ottieni_post
					id_media data = media.getId_media().get(i);
				}

				if (rimanenti > 0 && media.getPaginazione() != null) {
					iterazione_ottieni_media(user, access_token, media.getPaginazione().getCursori().getSuccessivo(), rimanenti);
				}
			}

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Tools.pr(media.getPaging().getCursors().getAfter());
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
				// Tools.pr(config.opzioni.getLimite());
				iterazione_ottieni_media(user, access_token, "", config.opzioni.getLimite());
			} catch (Exception e) {

			}

		}

		return lista_utenti;
	}
}
