package com.truenorth.campsite.booking.exception;

import com.truenorth.campsite.exception.BusinessError;
import com.truenorth.campsite.exception.BusinessException;

public class BookingModifiedException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public BookingModifiedException() {
		super(BusinessError.ERROR_BOOKING_MODIFIED);
	}

}
