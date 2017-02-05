package com.sainsburys.scraper.scrape;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class ScrapeToolsTest {
	
	ScrapeTools scrapeTools = new ScrapeTools();

	@Test
	public void test1KBPage() {
		NameValuePair header = new NameValuePair("Content-Length", "1024");
		List<NameValuePair> mockHeaders = new ArrayList<NameValuePair>();
		mockHeaders.add(header);
		
		Page mockedPage = mock(Page.class);
		WebResponse mockedWebResponse = mock(WebResponse.class);
	    when(mockedPage.getWebResponse()).thenReturn(mockedWebResponse);
	    when(mockedWebResponse.getResponseHeaders()).thenReturn(mockHeaders);
		
		assertEquals("1kb", scrapeTools.getPageSize(mockedPage));
	}
	
	@Test
	public void testRoundUp() {
		NameValuePair header = new NameValuePair("Content-Length", "1797");
		List<NameValuePair> mockHeaders = new ArrayList<NameValuePair>();
		mockHeaders.add(header);
		
		Page mockedPage = mock(Page.class);
		WebResponse mockedWebResponse = mock(WebResponse.class);
	    when(mockedPage.getWebResponse()).thenReturn(mockedWebResponse);
	    when(mockedWebResponse.getResponseHeaders()).thenReturn(mockHeaders);
		
		assertEquals("1.8kb", scrapeTools.getPageSize(mockedPage));
	}

}
