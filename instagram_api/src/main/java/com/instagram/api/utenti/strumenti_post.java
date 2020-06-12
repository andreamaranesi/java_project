package com.instagram.api.utenti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.instagram.api.config_generali.dimensioni_media;
import com.instagram.api.config_generali.dimensioni_px;
import com.instagram.api.config_generali.opzioni_filtri;
import com.instagram.api.strumenti_rapidi.shortcodes;

public abstract class strumenti_post extends manipola_data_instagram {

	protected String ritorna_tipo_media(String media) {
		if(media.contains("IMAGE"))
			return "IMMAGINE";
		return media;
	}

	public HashMap<String, Object> getDimensioni(String media_url, boolean VIDEO) {
		HashMap<String, Object> hashmap = new HashMap();

		try {
			URL url = new URL(media_url);
			URLConnection stream;
			stream = url.openConnection();
			hashmap.put("dimensione", stream.getContentLengthLong());
			if (!VIDEO) {
				BufferedImage immagine = ImageIO.read(stream.getInputStream());
				hashmap.put("altezza", immagine.getHeight());
				hashmap.put("larghezza", immagine.getWidth());

			} else {
				hashmap.put("altezza", -1);
				hashmap.put("larghezza", -1);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashmap;

	}

	private String filtra_hashtag(String hashtag) {

		Pattern pattern = Pattern.compile("^([^,;\\#\n])*");
		

		try {
			Matcher m = pattern.matcher(hashtag);
			while (m.find()) {
				return m.group();
			}

		} catch (Exception e) {
			shortcodes.pr(e.getLocalizedMessage());

		}

		return "";

	}
	
	protected ArrayList<String> hashtag(String descrizione) {
		if (descrizione == null)
			return null;
		else {
			ArrayList<String> _hashtag = new ArrayList();
		

			int pos_hashtag = 0;

			while (pos_hashtag != -1) {
				pos_hashtag = descrizione.indexOf("#");

				if (pos_hashtag != -1) {

					String temp = descrizione.substring(pos_hashtag + 1, descrizione.length());
					int pos_spaziovuoto = temp.indexOf(' ');
					pos_spaziovuoto = pos_spaziovuoto == -1 ? temp.length() : pos_spaziovuoto;
					String hashtag = temp.substring(0, pos_spaziovuoto);
					hashtag = filtra_hashtag(hashtag);
					_hashtag.add(hashtag);
					descrizione = descrizione.substring(pos_hashtag + 1 + hashtag.length(), descrizione.length());
				}

			}

			return _hashtag;

		}
	}
}
