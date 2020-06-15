package com.instagram.api.config_generali;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.instagram.api.eccezioni.cifra_errata;
import com.instagram.api.eccezioni.stringa_errata;

class opzioni_filtriTest extends opzioni_filtri{


	@Test
	void limite_post() {
		assertThrows(cifra_errata.class, ()->super.setLimite(-1));
	}

	@Test
	void tipo_dati_post() {
		assertThrows(cifra_errata.class, ()->super.opzioni_post.setTipo_dati(-1));
	}
	@Test
	void verifica() {
		int limite_post=super.getLimite();
		assertEquals(limite_post,50);
	}
	

}
