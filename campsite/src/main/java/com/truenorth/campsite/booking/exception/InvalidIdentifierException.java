package com.truenorth.campsite.booking.exception;

import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.exception.BusinessError;

public class InvalidIdentifierException extends BusinessException{
	

	private static final long serialVersionUID = 1L;

	public InvalidIdentifierException() {
		super(BusinessError.ERROR_INVALID_IDENTIFIER);
	}

}
