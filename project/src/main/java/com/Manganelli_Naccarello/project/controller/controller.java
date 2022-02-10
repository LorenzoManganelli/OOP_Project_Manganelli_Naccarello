package com.Manganelli_Naccarello.project.controller;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.service.*;
import com.Manganelli_Naccarello.project.filters.Filters;
import com.Manganelli_Naccarello.project.filters.FiltroGenerico;
import com.Manganelli_Naccarello.project.filters.FiltroMed;
import com.Manganelli_Naccarello.project.filters.FiltroMin;

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
	Filters filtri = new Filters();

	@GetMapping (value="/wind")
		public static String wind(@RequestParam String cityName)
		{
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		String meteo = servizioWind.getMeteo(cityName);
		String data = "\"date\":" + servizioWind.getDateTime();
		String wind = data + "\n" + servizioWind.cercaStat(meteo, "wind", 125) + "\n";
		System.out.println(service.salva(cityName, wind, path));
		return wind;
		}
	
	@GetMapping (value= "/media")
		public String media (@RequestParam String cityName, String dataOraInizio, String dataOraFine){
		String ritorno = "";
		FiltroMed filtroMed = new FiltroMed(cityName, dataOraInizio, dataOraInizio);
		//ritorno = filtri.filtroMedia(cityName, dataOraInizio, dataOraFine);
		ritorno = filtroMed.filtro();
		return ritorno;
	}
	
	@GetMapping (value= "/min")
	public String min (@RequestParam String cityName, String dataOraInizio, String dataOraFine){
		String ritorno = "";
		FiltroMin filtroMin = new FiltroMin(cityName, dataOraInizio, dataOraInizio);
		//ritorno = filtri.filtroMin(cityName, dataOraInizio, dataOraFine);
		ritorno = filtroMin.filtro();
		return ritorno;
	}
	
	@GetMapping (value= "/max")
	public String max (@RequestParam String cityName, String dataOraInizio, String dataOraFine){
		String ritorno = "";
		ritorno = filtri.filtroMax(cityName, dataOraInizio, dataOraFine);
		return ritorno;
	}

}
