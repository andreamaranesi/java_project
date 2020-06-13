package com.instagram.api.modelli;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.instagram.api.config_generali.configurazione;
import com.instagram.api.config_generali.credenziali_utenti;
import com.instagram.api.eccezioni.access_token_errato;
import com.instagram.api.eccezioni.stringa_errata;

class chiamate_APITest extends chiamate_API{

	
	@BeforeEach
	void setUp(){
		configurazione conf=new configurazione();
		credenziali_utenti cr=new credenziali_utenti();
		cr.setAccess_token(String.valueOf(Math.random()*20));
		conf.opzioni.credenziali_utenti.add(cr);
		super.config=conf;	
	}

	@Test
	void verifica_data_caricamento() {
		assertThrows(stringa_errata.class, ()->super.verifica_get_data_caricamento("prova"));

	}
	@Test
	void test() {
		assertThrows(access_token_errato.class, ()->super.nuova_chiamata_API());
	}

}
