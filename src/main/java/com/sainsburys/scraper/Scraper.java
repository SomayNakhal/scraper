package com.sainsburys.scraper;

import java.util.List;

import com.sainsburys.scraper.domain.Product;
import com.sainsburys.scraper.json.JsonTools;
import com.sainsburys.scraper.scrape.ScrapeTools;


public class Scraper {

	public static void main( String[] args ) {
		
		ScrapeTools scrapeTools = new ScrapeTools();
		List<Product> products = scrapeTools.getProductList(args[0]);
		
		JsonTools jsonTools = new JsonTools();
		String json = jsonTools.generateJson(products);
		System.out.println(json);
	}
}