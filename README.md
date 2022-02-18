# OOP_Project_Manganelli_Naccarello
## Controllo del vento
Questo programma, creato utilizzando java, permette di estrarre i dati del vento, divisi in velocità "speed", raffica "gust" e angolo "degree", dal'API OpenWeather. Inoltre salva questi dati su file, che a loro volta possono essere filtrati per i valori massimi, minimi e medi di tutte e tre le caratteristiche, scegliendo come range sia orari che date.

## Contenuti
1. [Introduzione](#introduzione)
2. [Installazione](#installazione)
3. [Rotte](#rotte)
4. [Test](#test)
5. [Documentazione](#documentazione)
6. [Eccezioni](#eccezioni)
7. [Struttura del progetto](#struttura)
8. [Progettisti](#progettisti)

<a name="introduzione"></a>
## Introduzione
Il programma è capace di estrarre dall'API i dati sopra segnati e li salva su file, e stampa sulla console il path per poterlo trovare. A sua volta questo file può essere filtrato per max, min e media della speed, gust e deg (già pronte ci sono 4 città: *Ancona, Tokyo, Sydney e Helsinki*).

<a name="installazione"></a>
## Installazione
Il programma può essere scaricato inserendo ```git clone https://github.com/LorenzoManganelli/OOP_Project_Manganelli_Naccarello.git```

<a name="rotte"></a>
## Rotte
Le rotte che l'utente può effettuare con l'utilizzo di Postman devono essere all'indirizzo: ```localhost:8080```

| Tipo  | Rotta | Descrizione |
| ------------- | ------------- | ------------- |
| GET  | /wind?cityName="*città*" | Restituisce le informazioni attuali del vento e le salva su file|
| GET  | /filter?filterType="*filtro*"&cityName="*città*"&dataOraInizio="*inizio*"&dataOraFine="*fine*" | Filtra tutti i dati del file, con la possibilità di filtrare per il **massimo** (*max, Max, MAX*), il **minimo** (*min, Min, MIN*) e **media** (*med, Med, MED*) |
| GET  | /print?cityName="*città*" | Stampa tutto il file di quella città sotto forma di JSONArray |

Per effetuare le richieste basta avviare il programma come SpringBoot e utilizzare Postman nel modo seguente 

## /wind?cityName="*città*"
Semplicemente inserire una qualsiasi città esistente al posto di "*città*", e Postman restituirà un JSONObject che ha i dati di speed, deg e gust e la data e ora corrente (in caso di errore sulla console verrà stampato un messaggio di errore)
 
![Screenshot (206)](https://user-images.githubusercontent.com/95304083/154550951-880b884b-c68d-41ae-b97d-d4a7aa9561e9.png)

## /filter?filterType="*filtro*"&cityName="*città*"&dataOraInizio="*inizio*"&dataOraFine="*fine*"
Inserire il tipo di filtro al posto di "*filtro*" (max, Max, MAX, min, Min, MIN, med, Med, MED), il nome dellà città al posto di "*città*" e la data di inizio e di fine che vuole essere analizzata (formato dd-MM-yyyy HH:mm:ss) al posto di "*inizio*" e "*fine*", restituendo alla fine un testo che mostra i dati richiesti con relative date e numero di dati analizzati.
 
**NOTA**: per usare i filtri si deve avere un file contenente i dati in maniera corretta
![Screenshot (207)](https://user-images.githubusercontent.com/95304083/154553283-47363ff5-c4bd-4079-b6a4-918e7f0f5be0.png)


## /print?cityName="*città*"
Semplicemente inserire una città con un file esistente al posto di "*città*", e Postman restituirà un file di testo contenente i dati all'interno del file.
 
![Screenshot (208)](https://user-images.githubusercontent.com/95304083/154558051-7b7316b3-ac23-4c36-a562-80f48fb0aa06.png)

<a name="test"></a>
## Test

<details><summary>Codice separato usato per fare i test</summary>
<p>
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
	


	public static void main(String[] args) {
		String stringa ="\"humidity\":45},\"visibility\":10000,\"wind\":{\"speed\":4.02,\"deg\":267},\"clouds\":22232eeeeee:eede";
		String stringa2 = "21-01-2022";
		String stringa3 = "\"date\": 21-01-2022 00:00:00";
		String stringa4 = "00:00:11";
		String stringa5 = "21-01-2022 00:00:00";
		String wind = cercaStat (stringa, "wind", 125);
		double speedValue = estraiStat(wind, "speed");
		double degvalue = estraiStat(wind, "deg");
		double gustvalue = estraiStat(wind, "gust");
		System.out.println(gustvalue);
		boolean prova = confrontaData(stringa3, stringa2);
		boolean prova2= confrontaOra(stringa3, stringa4);
		System.out.print(prova2);
		
		Date conversione = convertiDataOra(stringa5);
		System.out.print(conversione);
	}
		
	//"\"humidity\":45},\"visibility\":10000,\"wind\":{\"speed\":4.02,\"deg\":267,\"gust\":5.36},\"clouds\":";

	
	
	
	
	public static String cercaStat (String stringa, String stat, int carattereFinale) {
				    	
		    	String findWind = "";
		    	
		    	BufferedReader reader = new BufferedReader (new StringReader (stringa));
				BufferedWriter writer = new BufferedWriter (new StringWriter ());

		    	try {
		    		int next;

		    		
		    		while((next = reader.read()) != -1 ) {
// 34 = ", 125 = }, 44 = virgola, 58 = :
	
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
		    		System.out.println(findWind);
		    		reader.close();
			    	writer.close();
		    	}
		    	catch ( IOException e) { 
					System . out . println (" ERRORE di I/O");
					System . out . println (e);
		    	}
		    	catch(Exception e){
		    		System . out . println (e);
		    	}

		    	return findWind;
		    }
	
	
	//funzione che data una stringa (wind, generalmente) cerca una statistica precisa e ne estrae il valore restituendolo 
	//come double. pee prima cosa cerca la stat usando cercaStat, dopodichè rimuove l'ultimo carattere e procede a separare
	//il nome della stat dal suo valore.
	public static double estraiStat (String stringa, String stat) {

		String stringaEstratta = cercaStat(stringa, stat, 125);
		double statEstratta = 0;
		String numEstratto = "";

		if (stringaEstratta != null && stringaEstratta.length() > 0 && stringaEstratta.charAt(stringaEstratta.length() - 1) == '}') {
			stringaEstratta = stringaEstratta.substring(0, stringaEstratta.length() - 1);}
	
		System.out.println(stringaEstratta);
	
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
			System . out . println (" ERRORE di I/O");
			System . out . println (e);
    	}
    	catch(Exception e){
    		System . out . println (e);
    	}

		statEstratta = Double.parseDouble(numEstratto);
		System.out.println(statEstratta);
		return statEstratta;
	}

	public static boolean confrontaData(String data1, String data2) {
		String dataLetta = data1.substring(0, data1.length()-9);
		String daCercare = "\"date\": " + data2;
		if (daCercare.compareTo(dataLetta) == 0) return true;
		else return false;
	} 
	
	public static boolean confrontaOra(String ora1, String ora2) {
		String oraLetta = ora1.substring(19, ora1.length());
		System.out.println(oraLetta);
		if (ora2.compareTo(oraLetta) == 0) {
			System.out.println(ora2.compareTo(oraLetta));
			return true;
		} 
		else {
			System.out.println(ora2.compareTo(oraLetta));
			return false;}
	} 
	
	public static Date convertiDataOra (String dataOra) {

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
</p>
</details>

Ci interessava testare i metodi cercaStat, estraiStat e i vari confronti, quindi per farlo abbiamo creato un progetto di test separato e abbiamo fatto li tutti i nostri "esperimenti".

<a name="documentazione"></a>
## Documentazione
Il codice è documentato in [Javadoc](https://github.com).

<a name="eccezioni"></a>
## Eccezioni
Le eccezioni del programma sono le seguenti:
 
**EmptyStringException**: stampa un messaggio di errore in caso la stringa viene lasciata vuota, e viene usata nelle rotte **/wind**. **/filter** e **/print**

**FileNotFoundException**: stampa un messaggio di errore in caso il file non viene trovato dal programma (per esempio in caso la città sia stata scritta sbagliata), e viene usata nelle rotte **/filter** e **/print**

**PrevisioniNotFoundException**:  stampa un messaggio di errore quando la chiamata all'API non restituisce alcun risultato, e viene usata dalla rotta **/wind**.

<a name="struttura"></a>
## Struttura del progetto

Struttura originale del progetto

<a name="progettisti"></a>
### Progettisti
Lorenzo Manganelli e Raffaele Naccarello
