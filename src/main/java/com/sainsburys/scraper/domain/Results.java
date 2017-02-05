package com.sainsburys.scraper.domain;

import java.math.BigDecimal;
import java.util.List;

public class Results {
	
	private List<Product> results;
	private BigDecimal total;
	
	
	public List<Product> getResults() {
		return results;
	}
	public void setResults(List<Product> results) {
		this.results = results;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	

}
