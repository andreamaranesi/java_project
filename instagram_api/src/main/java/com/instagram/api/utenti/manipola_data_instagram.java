package com.instagram.api.utenti;

import java.util.HashMap;

import com.instagram.api.strumenti_rapidi.shortcodes;

public abstract class manipola_data_instagram {
	
	/**
	 * restituisce la data del post in formato <b>giorno-mese-anno - ora:minuti:secondi</b>
	 * @see #manipola_data(String)
	 * @param data_caricamento
	 * @return data formattata a partire dal timestamp restituito dalle API di instagram
	 */
	protected String data_formattata(String data_caricamento) {
		HashMap<String, Integer> info = manipola_data(data_caricamento);
		if (info == null)
			return null;
		return info.get("giorno").toString() + "/" + info.get("mese") + "/" + info.get("anno") + " - " + info.get("ora")
				+ ":" + info.get("minuti") + ":" + info.get("secondi");

	}

	/**
	 * Crea un'hashmap contenente giorno,mese,anno,minuto,ora,secondo relativi a quando il post è stato caricato
	 * 
	 * @param data_caricamento
	 */
	protected HashMap<String, Integer> manipola_data(String data_caricamento) {

		HashMap<String, Integer> finale = new HashMap();
		try {
			String[] splitted = data_caricamento.split("-");
			finale.put("anno", Integer.valueOf(splitted[0]));
			finale.put("mese", Integer.valueOf(splitted[1]));
			finale.put("giorno", Integer.valueOf(splitted[2].substring(0, 2)));
			String _finale = splitted[2];
			String[] _splitted = _finale.split(":");
			finale.put("ora", Integer.valueOf(_splitted[0].substring(3, _splitted[0].length())));
			finale.put("minuti", Integer.valueOf(_splitted[1]));
			finale.put("secondi", Integer.valueOf(_splitted[2].substring(0, 2)));
			return finale;
		} catch (Exception e) {
			return null;
		}

	}
}
