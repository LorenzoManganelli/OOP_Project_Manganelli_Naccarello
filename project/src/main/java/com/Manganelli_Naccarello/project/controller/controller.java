package com.Manganelli_Naccarello.project.controller;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.service.*;
import com.Manganelli_Naccarello.project.exceptions.*;
import com.Manganelli_Naccarello.project.filters.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class controller 
{
	
	@Autowired
	static service servizioWind = new service();


	@GetMapping (value = "/wind")
		public static String wind(@RequestParam String cityName)
							throws EmptyStringException, PrevisioniNotFoundException{
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		String meteo = servizioWind.getMeteo(cityName);
		if (meteo.isEmpty()) throw new PrevisioniNotFoundException("NON ESISTE ALCUNA CITTA' CON QUESTO NOME");
		String data = "\"date\":" + servizioWind.getDateTime();
		String wind = data + "\n" + servizioWind.cercaStat(meteo, "wind", 125) + "\n";
		System.out.println(servizioWind.salva(cityName, wind, path));
		return wind;
		}
	
	
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
	
	
	@GetMapping (value = "/media")
		public String media (@RequestParam String cityName, String dataOraInizio, String dataOraFine)
							throws EmptyStringException, FileNotFoundException {
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		if (dataOraInizio.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI INIZIO DELLA RICERCA.");
		if (dataOraFine.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI FINE DELLA RICERCA.");
		String ritorno = "";
		FiltroMedia media = new FiltroMedia(cityName, dataOraInizio, dataOraFine);
		ritorno = media.filtro();
		return ritorno;
	}
	
	@GetMapping (value = "/min")
	public String min (@RequestParam String cityName, String dataOraInizio, String dataOraFine)
							throws EmptyStringException, FileNotFoundException {
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		if (dataOraInizio.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI INIZIO DELLA RICERCA.");
		if (dataOraFine.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI FINE DELLA RICERCA.");
		String ritorno = "";
		FiltroMin min = new FiltroMin(cityName, dataOraInizio, dataOraFine);
		ritorno = min.filtro();
		return ritorno;
	}
	
	@GetMapping (value = "/max")
	public String max (@RequestParam String cityName, String dataOraInizio, String dataOraFine)
							throws EmptyStringException, FileNotFoundException{
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		if (dataOraInizio.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI INIZIO DELLA RICERCA.");
		if (dataOraFine.isEmpty()) throw new EmptyStringException("NON HAI INSERITO L'ORA DI FINE DELLA RICERCA.");
		String ritorno = "";
		FiltroMax max = new FiltroMax(cityName, dataOraInizio, dataOraFine);
		ritorno = max.filtro();
		return ritorno;
	}
	
	@GetMapping (value = "/print")	
	public String stampaFile (@RequestParam String cityName) 
							throws EmptyStringException, FileNotFoundException {
		
		
		if (cityName.isEmpty()) throw new EmptyStringException("NON HAI INSERITO LA CITTA' DA CERCARE.");
		City city = new City();
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
        city = servizioWind.leggiFile(path, cityName);
        return city.toString();
		
	}

	
}
