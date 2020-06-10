package com.instagram.api.eccezioni;

public class access_token_errato extends Exception {

	public access_token_errato (String messaggio){
		super(messaggio);
	}
	
}
