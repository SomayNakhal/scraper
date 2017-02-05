package com.sainsburys.scraper.json;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.scraper.domain.Product;
import com.sainsburys.scraper.domain.Results;

public class JsonTools {

	public String generateJson(List<Product> products) {
		String json="";		
		Results results = new Results();    		
		results.setResults(products);

		BigDecimal total = products.stream().map(Product::getUnit_price).reduce(BigDecimal.ZERO, BigDecimal::add);
		results.setTotal(total);

		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results);
		} catch (Throwable e) {
			System.err.println("Failed to generate JSON.");
			e.printStackTrace(System.err);
		}

		return json;
	}

}
