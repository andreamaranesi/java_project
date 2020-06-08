package com.instagram.api.ricorsione_post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class cursori {

	@JsonProperty("after")
	String successivo;

	public String getSuccessivo() {
		return successivo;
	}

	public void setSuccessivo(String successivo) {
		this.successivo = successivo;
	}

	
}
