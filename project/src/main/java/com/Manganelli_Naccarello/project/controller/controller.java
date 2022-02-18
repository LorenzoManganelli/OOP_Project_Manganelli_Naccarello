package com.Manganelli_Naccarello.project.controller;

import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.service.*;
import com.Manganelli_Naccarello.project.exceptions.*;
import com.Manganelli_Naccarello.project.filters.*;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/** Questo è il controller che gestisce le possibili chiamate al server
 * 
 * @author Raffaele
 * @author Lorenzo
 * */


@RestController
public class controller 
{
	
	@Autowired
	static ServiceImpl service = new ServiceImpl();

	/** Rotta di tipo GET che opera una chiamata all'API e restituisce le attuali condizioni meteo del vento.
	 * 
	 * @param il nome della città di cui si vogliono avere le previsioni meteo.
	 * @return la stringa contenente le informazioni sul vento.
	 * @throws EmptyStringException se l'utente non inserisce alcun parametro su Postman.
	 * @throws PrevisioniNotFoundException se la chiamata all'API non restituisce alcun risultato.
	 * */
	
	
	@GetMapping (value = "/wind")
		public static String wind(@RequestParam String cityName)
							throws EmptyStringException, PrevisioniNotFoundException{
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		String meteo = service.getMeteo(cityName);
		if (meteo.isEmpty()) throw new PrevisioniNotFoundException("NON ESISTE ALCUNA CITTA' CON QUESTO NOME");
		String data = "\"date\":" + service.getDateTime();
		String wind = data + "\n" + service.cercaStat(meteo, "wind", 125) + "\n";
		System.out.println(service.salva(wind, path));
		return wind;
		}
	
	/** Questa rotta di tipo GET permette all'utente di filtrare i dati salvati nei file per conoscere valori minimi, massimi o
	 * medi. 
	 * 
	 * @param il tipo di filtro che si vuole operare. Può essere MIN, MAX o MED.
	 * @param il nome della città su cui operare i filtri.
	 * @param la stringa contenete la data e ora di inizio filtraggio.
	 * @param la stringa contenete la data e ora di fine filtraggio.
	 * @return una stringa contenente i parametri filtrati.
	 * @throws EmptyStringException se l'utente non inserisce alcun parametro su Postman.
	 * @throws FileNotFoundException se il file da leggere non esiste o non viene trovato.
	 * */
	
	@GetMapping (value = "/filter")
		public String filter (@RequestParam String filterType, String cityName, String dataOraInizio, String dataOraFine)
							throws EmptyStringException, FileNotFoundException {
		if (filterType.isEmpty()) throw new EmptyStringException("NON HAI INSERITO UN FILTRO.");
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		if (dataOraInizio.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI INIZIO DELLA RICERCA.");
		if (dataOraFine.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI FINE DELLA RICERCA.");
		String ritorno = "";
		switch(filterType) {
		
		case "min":
		case "MIN":
		case "Min":
			{FiltroMin min = new FiltroMin(cityName, dataOraInizio, dataOraFine);
			ritorno = min.filtro(); break;}
			
		case "med":
		case "MED":
		case "Med":
			{FiltroMedia media = new FiltroMedia(cityName, dataOraInizio, dataOraFine);
			ritorno = media.filtro(); break;}
			
		case "max": 
		case "MAX":
		case "Max":
			{FiltroMax max = new FiltroMax(cityName, dataOraInizio, dataOraFine);
			ritorno = max.filtro(); break;}
			
		default: ritorno = "NON HAI INSERITO UN FILTRO VALIDO";
		}
		return ritorno;
		
	}

	/**Rotta di tipo GET che stampa il contenuto del file associato alla città scelta dall'utente.
	 * 
	 * @param il nome della città associata al file da leggere.
	 * @return una stringa contenente l'intero contenuto del file.
	 * @throws EmptyStringException se l'utente non inserisce alcun parametro su Postman.
	 * @throws FileNotFoundException se il file da leggere non esiste o non viene trovato.
	 * */
	
	@GetMapping (value = "/print")	
	public String stampaFile (@RequestParam String cityName) 
							throws EmptyStringException, FileNotFoundException {
		
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		City city = new City();
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
        city = service.leggiFile(path, cityName);
        return city.toString();
		
	}

	
}
