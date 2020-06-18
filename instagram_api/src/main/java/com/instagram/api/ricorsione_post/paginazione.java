package com.instagram.api.ricorsione_post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class paginazione {
	@JsonProperty("cursors")
	private cursori cursori;

	public cursori getCursori() {
		return cursori;
	}

	public void setCursori(cursori cursori) {
		this.cursori = cursori;
	}

	
	

}
