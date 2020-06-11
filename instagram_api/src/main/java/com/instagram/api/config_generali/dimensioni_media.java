package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.instagram.api.eccezioni.cifra_errata;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class dimensioni_media {

	private double min = 0;
	private double max = Double.POSITIVE_INFINITY;

	public double getMin() {
		return min;
	}

	public void setMin(double min) throws cifra_errata{
		if (min >= 0)
			this.min = min;
		else
			throw new cifra_errata("L'attributo min per la dimensione del post", min,0);
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) throws cifra_errata{
		if (max >= 0)
			this.max = max;
		else
			throw new cifra_errata("L'attributo max per la dimensione del post",max,0);
	}

}
