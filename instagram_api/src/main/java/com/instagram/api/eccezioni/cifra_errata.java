package com.instagram.api.eccezioni;

/**
 * la classe viene chiamata se si dovesse presentare una cifra errata
 * Es. <b>limite=-2</b> quando il limite <strong>deve</strong> essere compreso tra 1 e 2
 * 
 * @author Andrea Maranesi
 *
 */
public class cifra_errata extends Exception {

	public cifra_errata(String messaggio) {
		super(messaggio);
	}

	public cifra_errata(String nome, double min, int i) {
		super(nome
				+ " inserito, " + min + ", è errato. Il valore deve essere maggiore o uguale di " + i);
	}

	public cifra_errata(String nome, double min, int i, int j) {
		super(nome
				+ " inserito, " + min + ", è errato. Il valore deve essere maggiore o uguale di " + i + " e minore o uguale di " + 
				j);
	}

}
