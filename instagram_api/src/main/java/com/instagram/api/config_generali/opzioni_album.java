package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.instagram.api.eccezioni.cifra_errata;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class opzioni_album {

	int limite = 10;

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) throws cifra_errata {
		if (this.limite >= 0 && this.limite <= 50)
			this.limite = limite;
		else
			throw new cifra_errata("L'attributo limite per il numero dei figli di un album da filtrare", limite, 0, 50);
	}

}
