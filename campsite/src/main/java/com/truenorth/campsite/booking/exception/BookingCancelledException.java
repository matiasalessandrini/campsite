package com.truenorth.campsite.booking.exception;

import com.truenorth.campsite.exception.BusinessError;
import com.truenorth.campsite.exception.BusinessException;

public class BookingCancelledException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public BookingCancelledException() {
		super(BusinessError.ERROR_BOOKING_ALREADY_CANCELLED);
	}
}
