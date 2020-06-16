package com.os.osworks.domain.exception;
// Essa classe representa um erro de negocio
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// Construtor da exception
	public NegocioException(String message) {
		super(message);
	}

}
