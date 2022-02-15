package com.Manganelli_Naccarello.project.exceptions;

public class FileNotFoundException extends Exception {

	private String messaggio;
	public FileNotFoundException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};
	
}
