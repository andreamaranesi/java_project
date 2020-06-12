package com.instagram.api.utenti;

import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.instagram.api.utenti.metadati.descrizione_attributo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class utente {

	long id;
	String username;
	public String follower;
	public ArrayList<post> posts=new ArrayList(); 
	@JsonIgnore
	public boolean metadati=false;
	
	public utente(boolean metadati) {
		this.metadati=metadati;
		posts.add(new post(true));
	}
	
	public utente() {
		
	}

	
	public Object getId() {
		if(this.metadati)
			return new descrizione_attributo("int","id dell'utente");
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Object getUsername() {
		if(this.metadati)
			return new descrizione_attributo("String","nome dell'utente");
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
