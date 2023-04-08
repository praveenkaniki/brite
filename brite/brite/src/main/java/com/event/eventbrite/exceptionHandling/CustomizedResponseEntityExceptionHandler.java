package com.event.eventbrite.exceptionHandling;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<EventResponse> HandleAllExceptions(Exception exception, WebRequest request) throws Exception{
		EventResponse errorDetails = new EventResponse(new Date(),exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<EventResponse>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(UserNotFoundException.class)
//	public final ResponseEntity<ErrorforUserNotFoundException>UserNotFoundExceptions (Exception exception, WebRequest request) throws Exception{
//		ErrorforUserNotFoundException errorDetails = new ErrorforUserNotFoundException(LocalDate.now(),"EVENT_NOT_FOUND - 404",exception.getMessage(),request.getDescription(false));
//		return new ResponseEntity<ErrorforUserNotFoundException>(errorDetails, HttpStatus.NOT_FOUND);
//	}
//	
	 @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		 EventResponse modelResponse = new EventResponse(new Date(),
	                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
	                request.getDescription(false));
	        return new ResponseEntity<>(modelResponse, HttpStatus.BAD_REQUEST);
	        //return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
	    }

}
