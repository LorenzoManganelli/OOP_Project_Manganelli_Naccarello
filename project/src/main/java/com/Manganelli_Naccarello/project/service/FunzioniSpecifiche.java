package com.Manganelli_Naccarello.project.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



	/* in questa classe inseriremo tutte le funzioni che menggiano "brutalmente" le 
	 * nostre informazioni per ottenere i dati che ci servono. Non so se terremo questa classe separata da service oppure
	 * se la integreremo ad essa. boh.
	 * */


public class FunzioniSpecifiche {

	
	
	/* Questa funzione legge la stringa STRINGA in cerca del parametro STAT che deve trovarsi necessariamente tra parentesi.
	 * Quando lo trova, inizia a scrivere tutto quello che segue finchè non incontra il CARATTEREFINALE.
	 * */
	public String cercaStat (String stringa, String stat, int carattereFinale) {
    	
    	String findWind = "";
    	
    	BufferedReader reader = new BufferedReader (new StringReader (stringa));
		BufferedWriter writer = new BufferedWriter (new StringWriter ());

    	try {
    		int next;
    		while((next = reader.read()) != -1 ) {

//34 = ", 125=}, 44 = virgola, 58 = :
    			
    			if (next == 34) {
    				
    				while((next = reader.read()) != -1 ) {
    					findWind += (char) next;

    					 if (next == 34) {
    						 if ((findWind.compareTo("\"" + stat + "\"") != 0)) {
    							 findWind = "\""; 
    							 }
    						 break; 
    					 }
    				} 

    				if ((findWind.compareTo("\"" + stat + "\"") == 0)) {

						while((next = reader.read()) != -1 ) {
	    					findWind += (char) next;

	    					if (next == carattereFinale) break;
	    				}break;		
					}
    			}
    		}
    		if (findWind.compareTo("\"") == 0) findWind="\""+stat+"\": "+"-1";
    		reader.close();
	    	writer.close();
    	}
    	catch (IOException e) { 
			System.out.println (" ERRORE di I/O");
			System.out.println (e);
    	}
    	catch(Exception e){
    		System.out.println (e);
    	}
    	return findWind;
    }
	
	
	//funzione che data una stringa (wind, generalmente) cerca una statistica precisa e ne estrae il valore restituendolo 
	//come double. pee prima cosa cerca la stat usando cercaStat, dopodichè rimuove l'ultimo carattere e procede a separare
	//il nome della stat dal suo valore.
	public  double estraiStat (String stringa, String stat) {

		String stringaEstratta = cercaStat(stringa, stat, 125);
		double statEstratta = 0;
		String numEstratto = "";

		if (stringaEstratta != null && stringaEstratta.length() > 0 && stringaEstratta.charAt(stringaEstratta.length() - 1) == '}') {
			stringaEstratta = stringaEstratta.substring(0, stringaEstratta.length() - 1);}
	
		BufferedReader reader = new BufferedReader (new StringReader (stringaEstratta));
		BufferedWriter writer = new BufferedWriter (new StringWriter ());

		try {
    		int next;
  
    		while((next = reader.read()) != -1 ) {
    			if (next == 58) {

    				while ((next = reader.read()) != -1) {
    					if (next == 44) break;
    					numEstratto += (char) next;
		
    				} break;
    			}
    		}
    		reader.close();
	    	writer.close();
		} 	
		catch ( IOException e) { 
			System.out.println (" ERRORE di I/O");
			System.out.println (e);
    	}
    	catch(Exception e){
    		System.out.println (e);
    	}

		statEstratta = Double.parseDouble(numEstratto);
		return statEstratta;
	}
	
	/* questa funzione confronta la data chiesta dall'utente (data2) con quella letta nelal classe city (data1). returna true se le date
	 * sono uguali*/
	public boolean confrontaData(String data1, String data2) {
		String dataLetta = data1.substring(0, data1.length()-9);
		if (data2.compareTo(dataLetta) == 0) return true;
		else return false;
	}
	
	/* questa funzione confronta l'ora chiesta dall'utente (ora2) con quella letta nella classe city (ora1). returna true se gli orari
	 * sono uguali*/
	public boolean confrontaOra(String ora1, String ora2) {
		String oraLetta = ora1.substring(11, ora1.length());
		if (ora2.compareTo(oraLetta) == 0) return true;
		else return false;
	} 
	
	public Date convertiDataOra (String dataOra) {

		SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date ritorno = null;
		{
			try {
				Date date = dateParser.parse(dataOra);
				ritorno = date;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		return ritorno;
	}
	
}



//ඞ
//ඞ