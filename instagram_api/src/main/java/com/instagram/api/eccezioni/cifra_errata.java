package com.instagram.api.eccezioni;

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
