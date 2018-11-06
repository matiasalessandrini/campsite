package com.truenorth.campsite.exception;

public enum BusinessError{
	ERROR_FULL_CAMPSITE("error.full.campsite"), 
	ERROR_MIN_BOOKING_DAYS("error.min.booking.days"), 
	ERROR_MAX_BOOKING_DAYS("error.max.booking.days"), 
	ERROR_EARLY_BOOKING("error.early.booking"), 
	ERROR_LATE_BOOKING("error.late.booking"), 
	ERROR_INVALID_IDENTIFIER("error.invalid.identifier"), 
	ERROR_SPOT_RECENTLY_BOOKED("error.spot.recently.booked"), 
	ERROR_BOOKING_ALREADY_CANCELLED("error.booking.already.cancelled");
	
	private final String errorMessageCode;
	
	public String getErrorMessageCode() {
		return errorMessageCode;
	}

	BusinessError(String errorMessageCode) {
        this.errorMessageCode = errorMessageCode;
    }

}
