package com.nhan.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNotFoundException(NotFoundException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleEntityExistsException(EntityExistsException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processUnmergeException(final MethodArgumentNotValidException ex) {
		
		Map<String, String> errors= new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
				 String fieldName = ((FieldError) error).getField();
				 String errorMessage = error.getDefaultMessage();
				 errors.put(fieldName, errorMessage);
		});

        String errorMsg= "";

        for(String key: errors.keySet()){
            errorMsg += key + " : " + errors.get(key) + " \n";
        }
	
        return new ErrorResponse(HttpStatus.BAD_REQUEST, errorMsg);
    }
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleInternalServerError(Exception ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
	
}
