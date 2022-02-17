package com.Manganelli_Naccarello.project.exceptions;

/** Questa classe contiene l'eccezione lanciata quando il programma non trova il file richiesto.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class FileNotFoundException extends Exception {

	private String messaggio;
	
	/** Costruttore della classe.
	 * */
	public FileNotFoundException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};
}
