package com.Manganelli_Naccarello.project.service;

import com.Manganelli_Naccarello.project.model.City;
import com.Manganelli_Naccarello.project.model.WindData;
import com.Manganelli_Naccarello.project.exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.BufferedWriter;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/** Questa classe contiene le funzioni richiamate dal controller e dai filtri.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

@Service
public class ServiceImpl implements com.Manganelli_Naccarello.project.service.Service
{
	/** key è la chiave necessaria per poter eseguire la chiamata all'API.
	 * */
	private static String key = "6f1d40fe1384d12abc18e99d597fbd5d";	
	
	/** Questo metodo restituisce una stringa contenente la data e l'ora al momento della chiamata.
	 * 
	 * @return una stringa contenente data e ora.
	 * */
	public String getDateTime() {
		
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	/** Questo metodo opera la chiamata all'API e prende le previsioni meteo complete. 
	 * 
	 * @param il nome della città di cui si vogliono avere le previsioni meteo.
	 * @return la stringa contenente la previsione meteo completa.
	 * */
    public String getMeteo(String cityName) {
    	
    	String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+key;
        String meteo = "";

        try {

            URLConnection openConnection = (URLConnection) new URL(url).openConnection();
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(openConnection.getInputStream())));
            meteo += in.nextLine();
            in.close();

        } catch (IOException e) {
        	System.out.println("ERRORE DURANTE LA RICHIESTA METEO");
        } catch (Exception e) {
        	System.out.println("ERRORE GENERICO DEL METODO");
        }
       meteo += "\n" + "Data: " + getDateTime();
       return meteo;

    }
   
	/** Questo metodo cerca una statistica precisa all'interno della stringa di previsioni meteo e la estrae. Il metodo legge la 
	 * stringa previsione e quando trova il parametro statistica, inizia a scriverelo finchè non trova il carattere finale 
	 * della stringa (di solito una parentesi graffa chiusa). Viene usato per ricavare le previsioni del vento dalla 
	 * stringa delle previsioni complete e viene richiamato dal metodo estraiStat per ricavare le singole statistiche che 
	 * compongono le previsioni del vento.
	 * 
	 * 
	 * @param la stringa contenente le previsioni.
	 * @param la statistica da trovare all'interno della stringa previsioni.
	 * @param il carattere finale della stringa previsione castato ad int.
	 * @return la stringa contenente tutte le informazioni della statistica estratta.
	 * */
	public String cercaStat (String previsione, String stat, int carattereFinale) {
    	
    	String findStat = "";
    	
    	BufferedReader reader = new BufferedReader (new StringReader (previsione));
		BufferedWriter writer = new BufferedWriter (new StringWriter ());

    	try {
    		int next;
    		while((next = reader.read()) != -1 ) {

//valori int di alcuni segni di punteggiatura:      34 = "        125 = }        44 = ,       58 = :
    			
    			if (next == 34) {
    				
    				while((next = reader.read()) != -1 ) {
    					findStat += (char) next;

    					 if (next == 34) {
    						 if ((findStat.compareTo("\"" + stat + "\"") != 0)) {
    							 findStat = "\""; 
    							 }
    						 break; 
    					 }
    				} 

    				if ((findStat.compareTo("\"" + stat + "\"") == 0)) {

						while((next = reader.read()) != -1 ) {
	    					findStat += (char) next;

	    					if (next == carattereFinale) break;
	    				}break;		
					}
    			}
    		}
    		if (findStat.compareTo("\"") == 0) findStat="\""+stat+"\": "+"-1";
    		reader.close();
	    	writer.close();
    	}
    	catch (IOException e) { 
			System.out.println (" ERRORE DI I/O");

    	}
    	catch(Exception e){
    		System.out.println (" ERRORE DEL METODO");
    	}
    	return findStat;
    }
	
	/** Questo metodo estrae da una stringa di previsioni il valore numerico in double assegnato al parametro cercato. 
	 * 
	 * @param la stringa contenente le previsioni.
	 * @param la statistica da trovare all'interno della stringa previsioni, da cui estrarre il valore.
	 * @return il valore in double della statistica cercata.
	 * */
	public double estraiStat (String previsione, String stat) {

		String stringaEstratta = cercaStat(previsione, stat, 125);
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
		catch (IOException e) { 
			System.out.println (" ERRORE di I/O");
    	}
    	catch(Exception e){
    		System.out.println (" ERRORE DEL METODO");
    	}

		statEstratta = Double.parseDouble(numEstratto);
		return statEstratta;
	}
	
	/** Questo metodo salva una stringa contenente una previsione nel file assegnato alla citttà della previsione. 
	 * Se il file non esiste, allora il metodo crea un nuovo file.
	 * 
	 * @param è la stringa contenente la previsione da salvare.
	 * @param è il path del file su cui salvare la previsione.
	 * @return il path dove è stata salvata la previsione.
	 * */
	public String salva (String previsione, String path) {

		File file = new File(path);
		
		try{
            if(!file.exists()) {
                file.createNewFile();
            }
		}
		catch (Exception e) {
			System.out.println ("ERRORE DURANTE LA CREAZIONE DEL FILE");
			System.out.println (e);
		}
		
		try {
	
			int next ;
			BufferedReader reader = new BufferedReader (new StringReader (previsione));
			BufferedWriter writer = new BufferedWriter (new FileWriter (file, true));
			
			do {
				next = reader.read ();
				if ( next != -1) {
					char c = ( char ) next ;
					writer.write (c);
				}
			
			} while ( next != -1);
				reader.close ();
				writer.close ();
			}
		catch (IOException e) { 
			System.out.println (" ERRORE DI I/O");
			System.out.println (e);
		}
		return path;
	}
	
	/** Questo metodo legge un file contenente le informazioni del vento ricavate dalle passate chiamate all'API e popola
	 * un oggetto di tipo City con le informazioni scritte nel file.
	 * 
	 * @param il path del file da leggere.
	 * @param il nome della città associata al file.
	 * @return un oggetto di tipo City contenente le informazioni scrite sul file.
	 * @throws FileNotFoundException se il file cercato non viene trovato.
	 * */
    public City leggiFile (String path, String cityName) throws FileNotFoundException{
    	
    	City city = new City();
    	File file = new File(path);
    	if (!file.exists()) throw new FileNotFoundException("NON ESISTE ALCUN FILE RELATIVO A QUESTA CITTA'");
    	
    	try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new StringWriter ());

            city.setName(cityName);

            StringBuffer buffer=new StringBuffer(); 

            String linea;

            while((linea=reader.readLine())!=null)
            {
                WindData prev = new WindData();
                buffer.append(linea);
                linea = linea.substring(7, linea.length());
                prev.setDataOra(linea);

                linea = reader.readLine();

                double speedValue = estraiStat(linea, "speed");
                double degValue = estraiStat(linea, "deg");
                double gustValue = estraiStat(linea, "gust");

                prev.setWindSpeed(speedValue);
                prev.setWindDeg(degValue);
                prev.setWindGust(gustValue);

                city.addPrevisioni(prev);
            }
        	reader.close();
            writer.close();
            }
    	
    	catch (IOException e) {
    		System.out.print("ERRORE DI INPUT/OUTPUT");
    		}
    	return city;
    	}
	
	/** Questo metodo converte la stringa contenenete data e ora in formato Date per poter essere analizzata dai filtri.
	 * 
	 * @param la stringa contenenete data e ora.
	 * @return un oggetto di tipo Date contenente data e ora formattati in modo da essere analizzato dai filtri.
	 * */
	public Date convertiDataOra (String dataOra) {

		SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date ritorno = null;
		{
			try {
				Date date = dateParser.parse(dataOra);
				ritorno = date;
				} catch (ParseException e) {
					System.out.println("HAI INSERITO LA DATA NEL FORMATO SBAGLIATO");
				}
			}
		return ritorno;
	}
}


//ඞ
