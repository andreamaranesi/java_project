package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class lunghezza_desc {

	double min = 0;
	double max = Double.POSITIVE_INFINITY;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		if (min >= 0)
			this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		if (max > 0)
			this.max = max;
	}

}
