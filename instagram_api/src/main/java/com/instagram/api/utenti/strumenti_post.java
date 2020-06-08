package com.instagram.api.utenti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.imageio.ImageIO;

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



}
