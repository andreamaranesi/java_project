package com.instagram.api.modelli;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.api.config_generali.opzioni_statistiche;
import com.instagram.api.utenti.lista_utenti;

@Controller
public class metadati {

	public lista_utenti lista_utenti = new lista_utenti(true);

	/**
	 * 
	 * metodo per ottenere i metadati
	 * 
	 * @return JSON metadati
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/metadati", produces = "application/json")
	@ResponseBody
	public Object ottieni_metadati() {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(lista_utenti);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "errore";
	}

}
