package com.Manganelli_Naccarello.project.service;

import java.util.Date;

import com.Manganelli_Naccarello.project.exceptions.FileNotFoundException;
import com.Manganelli_Naccarello.project.model.City;

public interface Service {
	
	public String getDateTime();
	public String getMeteo(String cityName);
	public String cercaStat (String previsione, String stat, int carattereFinale);
	public double estraiStat (String previsione, String stat);
	public String salva (String previsione, String path);
	public City leggiFile (String path, String cityName) throws FileNotFoundException;
	public Date convertiDataOra (String dataOra);

}
