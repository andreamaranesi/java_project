package com.instagram.api.config_generali;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class dimensioni_media {

	private double min = 0;
	private double max = Double.POSITIVE_INFINITY;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		if (this.min >= 0)
			this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		if (this.max >= 0)
			this.max = max;
	}

}
