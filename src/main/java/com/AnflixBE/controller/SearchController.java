package com.AnflixBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AnflixBE.facade.SearchFacade;
import com.AnflixBE.modal.SearchRequest;
import com.AnflixBE.modal.ServiceHttpResponse;

@RestController
public class SearchController {
	
	@Autowired
	private SearchFacade searchFacade;

	@PostMapping(value="/searchQuery")
	public ServiceHttpResponse search(@RequestBody SearchRequest searchRequest) {
		return searchFacade.search(searchRequest);
	}
	
	@PostMapping(value="/searchTitle")
	public ServiceHttpResponse searchTitle(@RequestBody SearchRequest searchRequest) {
		return searchFacade.searchTitle(searchRequest);
	}
}
