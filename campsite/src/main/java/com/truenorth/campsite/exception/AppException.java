package com.truenorth.campsite.exception;

public abstract class AppException extends Exception{

	private static final long serialVersionUID = 1L;

    private final String code;
    private Object[] parameters;

	public AppException(String code) {
        super();
        this.code = code;
    }

    public AppException(Throwable cause, String code, String message, Object... parameters) {
        super(message, cause);
        this.code = code;
        this.parameters = parameters;
    }

    public AppException(String code, String message, Object... parameters) {
        super(message);
        this.code = code;
        this.parameters = parameters;
    }

    public AppException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
    
    public Object[] getParameters() {
		return parameters;
	}
}
