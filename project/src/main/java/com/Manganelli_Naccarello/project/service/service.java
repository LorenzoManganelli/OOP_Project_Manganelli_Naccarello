package com.Manganelli_Naccarello.project.service;

import com.Manganelli_Naccarello.project.model.City;
import com.Manganelli_Naccarello.project.model.WindData;
import com.Manganelli_Naccarello.project.service.FunzioniSpecifiche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.BufferedWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class service
{

	private static String key = "6f1d40fe1384d12abc18e99d597fbd5d";
	FunzioniSpecifiche funz = new FunzioniSpecifiche();	
	// Funzione per aggiungere una data al JSONObject con i dati meteo
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	
	
	//Metodo che salva su file. prende in ingresso il nome della città, le previsioni appena prese e il path. returna il path.
	public static String salva (String cityName ,String previsione, String path) {

		File file = new File(path);
		
		try{
            if(!file.exists()) {
                file.createNewFile();
            }
		}
		catch ( Exception e) {
			System.out.println (" ERRORE DURANTE  LA CREAZIONE DEL FILE");
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
		catch ( IOException e) { 
			System.out.println (" ERRORE di I/O");
			System.out.println (e);
		}
		return path;
	}

	
	//Funzione che prende dall'api i dati del meteo. Questa  funzione restituisce tutto come una stringa.
    public String getMeteo(String cityName) {
    	
    	String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+key;
        String meteo_citta = "";

        try {

            URLConnection openConnection = (URLConnection) new URL(url).openConnection();
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(openConnection.getInputStream())));
            meteo_citta += in.nextLine();
            System.out.println(meteo_citta);
            in.close();

        } catch (IOException e) {
        	System.out.println("ERRORE DURANTE LA RICHIESTA METEO");
        } catch (Exception e) {
        	System.out.println("ERRORE generico della classe");
        }
       meteo_citta += "\n" + "Data: " + getDateTime();
       return meteo_citta;

    }
    
    public City leggiFile (String path, String cityName){
    	City city = new City();
    	try {
    		
    		File file = new File(path);

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

                double speedValue = funz.estraiStat(linea, "speed");
                double degValue = funz.estraiStat(linea, "deg");
                double gustValue = funz.estraiStat(linea, "gust");

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
    		e.printStackTrace();
    		}
    	return city;
    	}
}

 



//ඞ
