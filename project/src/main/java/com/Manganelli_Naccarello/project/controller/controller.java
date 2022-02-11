package com.Manganelli_Naccarello.project.controller;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.service.*;
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
		FiltroMedia media = new FiltroMedia(cityName, dataOraInizio, dataOraFine);
		ritorno = media.filtro();
		return ritorno;
	}
	
	@GetMapping (value= "/min")
	public String min (@RequestParam String cityName, String dataOraInizio, String dataOraFine){
		String ritorno = "";
		FiltroMin min = new FiltroMin(cityName, dataOraInizio, dataOraFine);
		ritorno = min.filtro();
		return ritorno;
	}
	
	@GetMapping (value= "/max")
	public String max (@RequestParam String cityName, String dataOraInizio, String dataOraFine){
		String ritorno = "";
		FiltroMax max = new FiltroMax(cityName, dataOraInizio, dataOraFine);
		ritorno = max.filtro();
		return ritorno;
	}

}
