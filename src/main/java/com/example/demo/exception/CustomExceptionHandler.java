package com.example.demo.exception;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.user.UserNotFoundException;

/**
 * Methods of this class are partially copied from extended super class 'ResponseEntityExceptionHandler'
 * @ControllerAdvice or @RestControllerAdvice is used when we want to apply some functionality across all controllers.
 * So if any exception occurs across any controller, it will be handled by the methods of this class.
 * 
 * @author Amit, 02-Jul-2019
 */

//@ControllerAdvice
@RestControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	//Method to handle all generic exceptions
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Method to handle our customized 'UserNotFoundException'
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResp, HttpStatus.NOT_FOUND);
	}
	
	//This method is invoked when binding to a specific method argument is failed: e.g. constraint to some argument(size/past) is failed, as mentioned in User bean
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

//		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
		
//		String errors = ex.getBindingResult().getAllErrors().stream().map(err -> err.toString()).collect(Collectors.joining(", "));
//		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), "Validation Failed", errors);
		
		String errors = ex.getBindingResult().getAllErrors().stream().map(objError -> objError.getDefaultMessage()).collect(Collectors.joining(", "));
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), "Validation Failed", errors);
		
		return new ResponseEntity<>(exceptionResp, HttpStatus.BAD_REQUEST);
	}
}
