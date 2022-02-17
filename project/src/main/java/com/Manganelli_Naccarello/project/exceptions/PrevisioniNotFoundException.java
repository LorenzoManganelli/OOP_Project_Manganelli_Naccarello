package com.Manganelli_Naccarello.project.exceptions;

/** Questa classe contiene l'eccezione lanciata quando la chiamata all'API non restituisce alcun risultato.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class PrevisioniNotFoundException extends Exception {
	
	private String messaggio;
	
	/** Costruttore della classe.
	 * */
	public PrevisioniNotFoundException (String messaggio) {
		this.messaggio = messaggio;
		System.out.print(messaggio);
	};
}
