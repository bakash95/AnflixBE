package com.AnflixBE.modal;

import com.fasterxml.jackson.databind.JsonNode;

public class SearchResponse extends ServiceHttpResponse {
	private JsonNode searchResponse;

	public JsonNode getSearchResponse() {
		return searchResponse;
	}

	public void setSearchResponse(JsonNode searchResponse) {
		this.searchResponse = searchResponse;
	}

}
