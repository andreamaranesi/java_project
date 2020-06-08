package com.instagram.api.utenti;

import java.util.HashMap;

public abstract class manipola_data_instagram {
	
	public HashMap<String, Integer> manipola_data(String data_caricamento) {

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
