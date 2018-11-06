package com.truenorth.campsite.campiste.exception;

import com.truenorth.campsite.exception.BusinessException;
import com.truenorth.campsite.exception.BusinessError;

public class CampsiteFullException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public CampsiteFullException() {
		super(BusinessError.ERROR_FULL_CAMPSITE);
	}

}
