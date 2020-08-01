package com.AnflixBE.service;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.AnflixBE.modal.SearchRequest;
import com.AnflixBE.modal.SearchResponse;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SearchService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
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

	@Cacheable(key = "#searchRequest.searchData", cacheNames = { "search" })
	public SearchResponse genericSearch(SearchRequest searchRequest) {
		SearchResponse searchResponse = new SearchResponse();
		ResponseEntity<JsonNode> resFromAPI = restTemplate
				.getForEntity(MessageFormat.format(searchQuery, apiKey, searchRequest.getSearchData()), JsonNode.class);
		if (resFromAPI.getBody() != null && resFromAPI.getBody().get("Search") != null) {
			searchResponse.setSearchResponse(resFromAPI.getBody().get("Search"));
		}
		return searchResponse;
	}

	@Async(value="searchThreadPool")
	@Cacheable(key = "#searchRequest.searchData", cacheNames = { "searchTitle" })
	public CompletableFuture<SearchResponse> searchTitle(SearchRequest searchRequest) {
		LOGGER.debug("starting search for title {} with thread {}", searchRequest.getSearchData(),
				Thread.currentThread().getName());
		SearchResponse searchResponse = new SearchResponse();
		ResponseEntity<JsonNode> resFromAPI = restTemplate
				.getForEntity(MessageFormat.format(searchTitle, apiKey, searchRequest.getSearchData()), JsonNode.class);
		if (resFromAPI.getBody() != null) {
			searchResponse.setSearchResponse(resFromAPI.getBody());
		}
		LOGGER.debug("search completed for title {} with thread {}", searchRequest.getSearchData(),
				Thread.currentThread().getName());
		return CompletableFuture.completedFuture(searchResponse);
	}
	
	@Bean("searchThreadPool")
	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(30);
		threadPoolTaskExecutor.setMaxPoolSize(50);
		return threadPoolTaskExecutor;
	}

}
