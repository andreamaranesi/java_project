package com.instagram.api.strumenti_rapidi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import com.instagram.api.eccezioni.eccezione;
import com.instagram.api.utenti.post;

public abstract class strumenti_comuni {

	/**
	 * Il seguente metodo restituisce una lista dinamica contente tutte le
	 * occorrenze in una stringa, distinti da un <b>separatore</b>.<br>
	 * Es. nome1,nome2 =>
	 * <b>separatore=","</b> => [nome1,nome2]
	 * 
	 * @param separatore
	 * @author Andrea Maranesi
	 */
	public ArrayList<String> cerca_valori(String stringa, String separatore) {
		ArrayList<String> valori_cercati = new ArrayList();
		int i;
		do {
			i = stringa.indexOf(separatore);
			int c = i == -1 ? stringa.length() : i;
			valori_cercati.add(stringa.substring(0, c));
			stringa = stringa.substring(i + separatore.length());
		} while (i != -1);
		return valori_cercati;
	}

	/**
	 * 
	 * verifica se il post e' stato caricato prima o dopo una determinata
	 * data_iniziale
	 * 
	 * @see #cerca_valori(String, String)
	 * @param data_iniziale
	 * @author Alessio Pettinari
	 */
	public boolean verifica_data(post post, String data_iniziale) {
		try {
			if (!data_iniziale.isEmpty()) {
				data_iniziale = data_iniziale.replaceAll("[\\s]", ""); // trim() non valido in questo caso
				HashMap<String, Integer> manipola_data = post.hashmap_data();
				int giorno_post = manipola_data.get("giorno");
				int mese_post = manipola_data.get("mese");
				int anno_post = manipola_data.get("anno");

				Calendar data_2 = Calendar.getInstance();
				data_2.set(anno_post, mese_post, giorno_post);

				ArrayList<String> date_iniziali = cerca_valori(data_iniziale, "and");
				for (int i = 0; i < date_iniziali.size(); i++) {
					String data_corrente = date_iniziali.get(i);
					String comparatore = data_corrente.substring(0, 1);
					data_corrente = data_corrente.substring(1, data_corrente.length());
					String[] data = data_corrente.split("-");
					int l = data.length;
					int prima_data = Integer.valueOf(data[0]);
					int giorno = l == 3 ? prima_data : 1;
					int mese = l == 3 ? Integer.valueOf(data[1]) : l == 2 ? prima_data : 1;
					int anno = l == 3 ? Integer.valueOf(data[2]) : l == 2 ? Integer.valueOf(data[1]) : prima_data;
					Calendar data_1 = Calendar.getInstance();

					data_1.set(anno, mese, giorno);

					int valore = 0;

					if (comparatore.contains(">")) {

						valore = data_2.compareTo(data_1);

					} else {
						valore = data_1.compareTo(data_2);
					}
					if (valore <= 0)
						return false;
				}
			}
			return true;
		} catch (Exception e) {

			return true;
		}
	}

	protected String leggi(String path) throws eccezione, FileNotFoundException {
			String stringa = "";
		File file = new File(path);
		if (!file.exists())
			throw new eccezione("Il file nella posizione " + path + " non esiste");
		Scanner scanner = new Scanner(new File(path));
		while (scanner.hasNext()) {
			stringa += scanner.next();
		}
		return stringa;
	}

}
