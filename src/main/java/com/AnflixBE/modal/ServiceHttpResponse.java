package com.AnflixBE.modal;

import org.springframework.http.HttpStatus;

public class ServiceHttpResponse {
	private String status;
	private HttpStatus httpStatus;
	private boolean isRedirection;
	private String redirectURI;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public boolean isRedirection() {
		return isRedirection;
	}

	public void setRedirection(boolean isRedirection) {
		this.isRedirection = isRedirection;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}

}
