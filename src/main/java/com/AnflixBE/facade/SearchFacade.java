package com.AnflixBE.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AnflixBE.modal.SearchRequest;
import com.AnflixBE.modal.SearchResponse;
import com.AnflixBE.modal.ServiceHttpResponse;
import com.AnflixBE.service.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class SearchFacade {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SearchService searchService;

	public ServiceHttpResponse search(SearchRequest searchRequest) {
		SearchResponse searchResponse = searchService.genericSearch(searchRequest);
		JsonNode searchData = searchResponse.getSearchResponse();
		List<Future> listOfFutures = new ArrayList<Future>();
		if (searchData != null) {
			ArrayNode arrayNode = (ArrayNode) searchData;
			for (JsonNode jsonNode : arrayNode) {
				String title = jsonNode.get("Title").asText();
				SearchRequest titleSearchRequest = new SearchRequest();
				titleSearchRequest.setSearchData(title);
				CompletableFuture<SearchResponse> titleSearch = (CompletableFuture<SearchResponse>) searchService
						.searchTitle(titleSearchRequest);
				listOfFutures.add(titleSearch);
				titleSearch.whenComplete(
						(result, ex) -> ((ObjectNode) jsonNode).put("movieData", result.getSearchResponse()));
			}
		}
		CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[listOfFutures.size()])).join();
		return searchResponse;
	}

	public ServiceHttpResponse searchTitle(SearchRequest searchRequest) {
		SearchResponse searchResponse = new SearchResponse();
		CompletableFuture<SearchResponse> future = searchService.searchTitle(searchRequest);
		try {
			searchResponse = future.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("exception in future execution ", e);
		}

		return searchResponse;
	}

}
