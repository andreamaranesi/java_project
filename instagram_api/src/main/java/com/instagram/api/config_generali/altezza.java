package com.instagram.api.config_generali;

import com.instagram.api.eccezioni.cifra_errata;

public class altezza {
	private double min = 0;
	private double max = Double.POSITIVE_INFINITY;

	public double getMin() {
		return min;
	}

	public void setMin(double min) throws cifra_errata{
		if (min >= 0)
			this.min = min;
		else
			throw new cifra_errata("L'attributo min per l'altezza in px del post ", min,0);
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) throws cifra_errata{
		if (max >= 0)
			this.max = max;
		else
			throw new cifra_errata("L'attributo max per l'altezza in px del post",max,0);
	}
}
