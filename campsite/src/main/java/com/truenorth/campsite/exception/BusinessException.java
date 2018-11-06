package com.truenorth.campsite.exception;

public class BusinessException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public BusinessException(BusinessError error, Object... parameters) {
		super(error.name(), error.getErrorMessageCode(), parameters);
	}

}
