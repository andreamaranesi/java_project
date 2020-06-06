package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class opzioni_statistiche {

	private boolean hashtag=true;
	private boolean like=true;
	private boolean commenti=true;
	private int limite_post=20;
	private boolean dimensione=true;
	
}
