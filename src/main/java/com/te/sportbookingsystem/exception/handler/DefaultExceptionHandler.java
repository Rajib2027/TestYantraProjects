package com.te.sportbookingsystem.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.te.sportbookingsystem.exception.InvalidException;
import com.te.sportbookingsystem.exception.InvalidUserCredentialException;
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler  {
	@ExceptionHandler(InvalidException.class)
	public final ResponseEntity<ErrorMessage>invalidInput(Exception exception){
    ErrorMessage exceptionResponse= new ErrorMessage(exception.getMessage(),"Kindly Check Your User Id");
    return new ResponseEntity<ErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
}
	@ExceptionHandler(InvalidUserCredentialException.class)
	public final ResponseEntity<ErrorMessage>logInPasswordInvalid(Exception exception){
    ErrorMessage exceptionResponse= new ErrorMessage(exception.getMessage(),"Kindly check your credentials");
    return new ResponseEntity<ErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
}
	
class ErrorMessage{
	private String message;
	private String details;
	public ErrorMessage(String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
}


