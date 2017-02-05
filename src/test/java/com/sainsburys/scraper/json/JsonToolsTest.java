package com.sainsburys.scraper.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.sainsburys.scraper.domain.Product;

public class JsonToolsTest extends JsonTools {

	JsonTools jsonTools = new JsonTools();

	@Test
	public void testOneProduct() throws JSONException {
		
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("Product 1", "1.3kb", new BigDecimal("1.50"), "desc1"));
		String expectedJson = "{\"results\":[{\"title\":\"Product 1\",\"size\": \"1.3kb\",\"unit_price\":1.50,\"description\":\"desc1\"} ],\"total\":1.50}";		
		
		String generatedJson = jsonTools.generateJson(products);
		
		JSONAssert.assertEquals(expectedJson,generatedJson,true);		
	}
	
	@Test
	public void testTwoProducts() throws JSONException {
		
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("Product 1", "1.3kb", new BigDecimal("1.50"), "desc1"));
		products.add(new Product("Product 2", "2.7kb", new BigDecimal("2.30"), "desc2"));

		String expectedJson = "{\"results\":[{\"title\":\"Product 1\", \"size\": \"1.3kb\",\"unit_price\":1.50,\"description\": \"desc1\"}"
								+",{\"title\":\"Product 2\", \"size\": \"2.7kb\",\"unit_price\":2.30,\"description\": \"desc2\"}],\"total\":3.80}";		
		
		String generatedJson = jsonTools.generateJson(products);
		
		JSONAssert.assertEquals(expectedJson,generatedJson,true);		
	}

}
