package com.Manganelli_Naccarello.project.exceptions;

/** Questa classe contiene l'eccezione lanciata quando l'utente immette una stringa vuota.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class EmptyStringException extends Exception {
	
	private String messaggio;
	
	/** Costruttore della classe.
	 * */
	public EmptyStringException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};
}
