package com.AnflixBE;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoPageFoundHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public void badRequest(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Hi");
		response.sendRedirect("/");
	}
}
