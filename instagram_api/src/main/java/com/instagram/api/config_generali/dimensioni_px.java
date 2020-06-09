package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class dimensioni_px {

	public altezza altezza=new altezza();
	public larghezza larghezza=new larghezza();

}
