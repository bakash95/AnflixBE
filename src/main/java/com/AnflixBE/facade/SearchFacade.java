package com.AnflixBE.facade;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.AnflixBE.modal.SearchRequest;
import com.AnflixBE.modal.SearchResponse;
import com.AnflixBE.modal.ServiceHttpResponse;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SearchFacade {

	private static final String API_KEY = "API_KEY";

	@Value("${search_query}")
	private String searchQuery;

	@Value("${search_title}")
	private String searchTitle;

	private String apiKey = System.getenv(API_KEY);

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Cacheable(key="#searchRequest.getSearchString")
	public ServiceHttpResponse search(SearchRequest searchRequest) {
		SearchResponse searchResponse = new SearchResponse();
		ResponseEntity<JsonNode> resFromAPI = restTemplate.getForEntity(
				MessageFormat.format(searchQuery, apiKey, searchRequest.getSearchData()), JsonNode.class);
		if (resFromAPI.getBody() != null && resFromAPI.getBody().get("Search") != null) {
			searchResponse.setSearchResponse(resFromAPI.getBody().get("Search"));
		}
		return searchResponse;
	}

	@Cacheable(key="#searchRequest.getSearchString")
	public ServiceHttpResponse searchTitle(SearchRequest searchRequest) {
		SearchResponse searchResponse = new SearchResponse();
		ResponseEntity<JsonNode> resFromAPI = restTemplate.getForEntity(
				MessageFormat.format(searchTitle, apiKey, searchRequest.getSearchData()), JsonNode.class);
		if (resFromAPI.getBody() != null) {
			searchResponse.setSearchResponse(resFromAPI.getBody());
		}
		return searchResponse;
	}

}
