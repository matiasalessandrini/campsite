package com.truenorth.campsite.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageResource {
	
	private static ResourceBundle messages = ResourceBundle.getBundle("messageResources");

	public static String getMessage(String key, Object...arguments){
		 String value = messages.getString(key);
		 return MessageFormat.format(value, arguments);
	}
	
}
