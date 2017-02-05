package com.sainsburys.scraper.scrape;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.sainsburys.scraper.domain.Product;

public class ScrapeTools {

	private static final int BYTES_IN_KB = 1024;
	private static final String CLASS_OF_PRODUCT_DIV = "product";
	private static final String CLASS_OF_PRICE_PARAGRAPH = "pricePerUnit";
	private static final String CLASS_OF_PRODUCT_ANCHOR = "productInfo";
	private static final String CLASS_OF_PRODUCT_DESCRIPTION_PARAGRAPH = "productText";
	
	private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";

	public List<Product> getProductList(String Url) {
		List<Product> products = new ArrayList<Product>();

		try (WebClient client = new WebClient()) {
			client.getOptions().setCssEnabled(false);  
			client.getOptions().setJavaScriptEnabled(false);  

			HtmlPage page = client.getPage(Url);

			List<?> productDivs = page.getByXPath("//div[normalize-space(./@class)='"+CLASS_OF_PRODUCT_DIV+"']");    		
			for(Object object: productDivs) {
				if (object instanceof HtmlDivision) {
					HtmlDivision div = (HtmlDivision)object;

					Product product = new Product();

					HtmlParagraph priceParagraph = div.getFirstByXPath(".//p[normalize-space(./@class)='"+CLASS_OF_PRICE_PARAGRAPH+"']");

					String price = priceParagraph.getChildNodes().stream().filter(x -> new Short(x.getNodeType()).equals(Node.TEXT_NODE))    			
							.findFirst()
							.get()
							.getTextContent()
							.replace("&pound", "")
							.replace("\u00a3", "")
							.trim();

					product.setUnit_price(new BigDecimal(price).setScale(2, RoundingMode.HALF_UP));

					HtmlAnchor productAnchor = (HtmlAnchor) div.getFirstByXPath(".//div[normalize-space(./@class)='"+CLASS_OF_PRODUCT_ANCHOR+"']/h3/a");
					product.setTitle(productAnchor.getTextContent().trim());
					HtmlPage productPage = client.getPage(productAnchor.getHrefAttribute());
					product.setSize(getPageSize(productPage));
					HtmlParagraph productDescParagraph = (HtmlParagraph)productPage.getFirstByXPath("//div[normalize-space(./@class)='"+CLASS_OF_PRODUCT_DESCRIPTION_PARAGRAPH+"']/p");
					product.setDescription(productDescParagraph.getTextContent().trim());

					products.add(product);
				}
			}

		} catch(Throwable e) {
			System.err.println("Failed to access product info.");
			e.printStackTrace();
		}

		return products;
	}


	public String getPageSize(Page page) {
		List<NameValuePair> headers =page.getWebResponse().getResponseHeaders();    	
		String sizeInBytes = headers.stream().filter(x -> x.getName().trim().equalsIgnoreCase(CONTENT_LENGTH_HEADER_NAME))
				.findFirst()
				.get()
				.getValue();

		int size = 0;
		if (sizeInBytes!= null) {
			try {
				size = Integer.parseInt(sizeInBytes);
			} catch (NumberFormatException e) {
				System.err.println("Invalid content length header. Defaulting to 0 size : " + sizeInBytes);
				e.printStackTrace(System.err);
			}
		} else {
			System.err.println("Missing content length header. Defaulting to 0 size.");
		}

		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format((double)size/BYTES_IN_KB)+"kb";
	}

}
