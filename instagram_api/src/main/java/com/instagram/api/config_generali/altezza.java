package com.instagram.api.config_generali;

import com.instagram.api.eccezioni.cifra_errata;

public class altezza {
	private int min = 0;
	private int max = Integer.MAX_VALUE;

	public int getMin() {
		return min;
	}
	
	public void setMin(int min) throws cifra_errata{
		if (min >= 0)
			this.min = min;
		else
			throw new cifra_errata("L'attributo min per l'altezza in px del post",min,0);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) throws cifra_errata{
		if (max >= 0)
			this.max = max;
		else
			throw new cifra_errata("L'attributo max per l'altezza in px del post",max,0);
	}
}
