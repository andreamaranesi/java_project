package com.instagram.api.eccezioni;

/**
 * La classe viene chiamata quando una stringa non rispetta il formato richiesto
 * 
 * @author Alessio Pettinari
 *
 */
public class stringa_errata extends Exception {

	public stringa_errata (String messaggio){
		super(messaggio);
	}
	
}
