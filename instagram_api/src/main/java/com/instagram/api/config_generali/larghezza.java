package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.instagram.api.eccezioni.cifra_errata;

@JsonIgnoreProperties(ignoreUnknown = true)
public class larghezza {
	double min = 0;
	double max = Double.POSITIVE_INFINITY;

	public double getMin() {
		return min;
	}

	public void setMin(double min) throws cifra_errata{
		if (min >= 0)
			this.min = min;
		else
			throw new cifra_errata("L'attributo min per la larghezza in px del post",min,0);
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) throws cifra_errata{
		if (max >= 0)
			this.max = max;
		else
			throw new cifra_errata("L'attributo max per la larghezza in px del post",max,0);
	}
}
