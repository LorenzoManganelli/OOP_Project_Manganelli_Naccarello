package com.Manganelli_Naccarello.project.exceptions;

public class PrevisioniNotFoundException extends Exception {
	
	private String messaggio;
	public PrevisioniNotFoundException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};

}
