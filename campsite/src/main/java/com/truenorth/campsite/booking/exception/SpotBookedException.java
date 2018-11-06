package com.truenorth.campsite.booking.exception;

import com.truenorth.campsite.exception.BusinessError;
import com.truenorth.campsite.exception.BusinessException;

public class SpotBookedException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public SpotBookedException() {
		super(BusinessError.ERROR_SPOT_RECENTLY_BOOKED);
	}

}
