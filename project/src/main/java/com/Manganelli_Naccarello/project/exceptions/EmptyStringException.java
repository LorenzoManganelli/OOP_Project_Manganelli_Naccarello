package com.Manganelli_Naccarello.project.exceptions;

public class EmptyStringException extends Exception {
	

	private String messaggio;
	public EmptyStringException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};
	

}
