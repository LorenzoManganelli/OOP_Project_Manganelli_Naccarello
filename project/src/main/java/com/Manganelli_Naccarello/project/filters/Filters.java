package com.Manganelli_Naccarello.project.filters;
import java.io.IOException;
import java.util.Date;

import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.service.*;

public class Filters {
	service service = new service();
	FunzioniSpecifiche funz = new FunzioniSpecifiche();
	
	/*Questa funzione converte tutte le date utili a DATE e confronta la data passata dall'utente con quelle segnate sulle 
	 * previsioni. quando raggiunge o supera la data di inizio passata dall'utente allora inizia a operare l'analisi delle
	 * previsioni richieste.
	 * */
	public String filtroMedia (String cityName, String dataOraInizio, String dataOraFine){
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		City city = new City();
		city = service.leggiFile(path, cityName);
		Date dataInizio = funz.convertiDataOra(dataOraInizio);
		Date dataFine = funz.convertiDataOra(dataOraFine);
		int n = 0;
		
		for (WindData dato: city.getPrevisioni()) {
			Date dataPrevisione = null;
			dataPrevisione = funz.convertiDataOra(city.getDataN(n));
			int fine = dataPrevisione.compareTo(dataFine);
			int confronto = dataInizio.compareTo(dataPrevisione);
			n++;
			if (confronto>=0) {
				double mediaSpeed = 0;
				int contaSpeed = 0;
				double mediaDeg = 0;
				int contaDeg = 0;
				double mediaGust = 0;
				int contaGust = 0;
				
				while (fine<0) {
					mediaSpeed += city.getSpeedN(n);
					contaSpeed++;
					mediaDeg += city.getDegN(n);
					contaDeg++;
					
					if (city.getGustN(n)!=-1) {
						mediaGust += city.getGustN(n);
						contaGust++;
					}
					n++;
					dataPrevisione = funz.convertiDataOra(city.getDataN(n));
					fine = dataPrevisione.compareTo(dataFine);
				}
				mediaSpeed= Math.round(mediaSpeed/contaSpeed*100.0)/100.0;
				mediaDeg= Math.round(mediaDeg/contaDeg*100.0)/100.0;
				mediaGust= Math.round(mediaGust/contaGust*100.0)/100.0;
				
				String ritorno = "";
				ritorno += "\"MEDIA Speed\": "+ mediaSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaSpeed + "\n";
				ritorno += "\"MEDIA Deg\": "+ mediaDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaDeg + "\n";
				ritorno += "\"MEDIA Gust\": "+ mediaGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaGust + "\n";
				return ritorno;
			}
		}
		
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
	
	
	public String filtroMin (String cityName, String dataOraInizio, String dataOraFine){
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		City city = new City();
		city = service.leggiFile(path, cityName);
		Date dataInizio = funz.convertiDataOra(dataOraInizio);
		System.out.println(dataInizio);
		Date dataFine = funz.convertiDataOra(dataOraFine);
		System.out.println(dataFine);
		int n = 0;
		
		for (WindData dato: city.getPrevisioni()) {
			System.out.println(n);
			Date dataPrevisione = null;
			dataPrevisione = funz.convertiDataOra(city.getDataN(n));
			System.out.println(dataPrevisione);
			int fine = dataPrevisione.compareTo(dataFine);
			System.out.println(fine);
			int confronto = dataPrevisione.compareTo(dataInizio);
			System.out.println(confronto);
			
			if (confronto>=0) {
				double minSpeed = 9999999;
				String dataMinSpeed = "";
				int contaSpeed = 0;
				double minDeg = 9999999;
				String dataMinDeg = "";
				int contaDeg = 0;
				double minGust = 99999999;
				String dataMinGust = "";
				int contaGust = 0;
				
				while (fine<0) {
					
					if (city.getSpeedN(n)<minSpeed) {
						minSpeed = city.getSpeedN(n);
						dataMinSpeed = city.getDataN(n);
						}
					System.out.println(city.getSpeedN(n));
					contaSpeed++;
					
					if (city.getDegN(n)<minDeg) {
						minDeg = city.getDegN(n);
						dataMinDeg = city.getDataN(n);
					}
					System.out.println(city.getDegN(n));
					contaDeg++;
					
					if (city.getGustN(n)<minGust) {
						if(city.getGustN(n)>0) {
							minGust += city.getGustN(n);
							System.out.println(minGust);
							dataMinGust = city.getDataN(n);
						}
					}
					System.out.println(city.getGustN(n));
					contaGust++;
					n++;
					dataPrevisione = funz.convertiDataOra(city.getDataN(n));
					fine = dataPrevisione.compareTo(dataFine);
				}
				minSpeed = Math.round(minSpeed*100.0)/100.0;
				minDeg = Math.round(minDeg*100.0)/100.0;
				minGust = Math.round(minGust*100.0)/100.0;
				
				String ritorno = "";
				ritorno += "\"MIN Speed\": "+ minSpeed + "\n";
				ritorno += "Ottenuta il giorno: "+ dataMinSpeed+ "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaSpeed + "\n";
				ritorno += "\"MIN Deg\": "+ minDeg + "\n";
				ritorno += "Ottenuta il giorno: "+ dataMinDeg+ "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaDeg + "\n";
				ritorno += "\"MIN Gust\": "+ minGust + "\n";
				ritorno += "Ottenuta il giorno: "+ dataMinGust+ "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaGust + "\n";
				return ritorno;
			}
			n++;
		}
		
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
	
	public String filtroMax (String cityName, String dataOraInizio, String dataOraFine) throws IOException{
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		City city = new City();
		city = service.leggiFile(path, cityName);
		Date dataInizio = funz.convertiDataOra(dataOraInizio);
		Date dataFine = funz.convertiDataOra(dataOraFine);
		
		for (WindData dato: city.getPrevisioni()) {
			int n = 0;
			Date dataPrevisione = null;
			dataPrevisione = funz.convertiDataOra(city.getDataN(n));
			int fine = dataPrevisione.compareTo(dataFine);
			int confronto = dataInizio.compareTo(dataPrevisione);
			
			if (confronto>=0) {
				double maxSpeed = 0;
				int contaSpeed = 0;
				double maxDeg = 0;
				int contaDeg = 0;
				double maxGust = 0;
				int contaGust = 0;
				
				while (fine<0) {
					if (city.getSpeedN(n)>maxSpeed) maxSpeed = city.getSpeedN(n);
					contaSpeed++;
					if (city.getDegN(n)>maxSpeed) maxDeg = city.getDegN(n);
					contaDeg++;
					if (city.getGustN(n)>maxSpeed && city.getGustN(n) != -1) maxGust += city.getGustN(n);
					contaGust++;
					n++;
					dataPrevisione = funz.convertiDataOra(city.getDataN(n));
					fine = dataPrevisione.compareTo(dataFine);
				}
				maxSpeed = Math.round(maxSpeed*100.0)/100.0;
				maxDeg = Math.round(maxDeg*100.0)/100.0;
				maxGust = Math.round(maxGust*100.0)/100.0;
				
				String ritorno = "";
				ritorno += "\"MAX Speed\": "+ maxSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaSpeed + "\n";
				ritorno += "\"MAX Deg\": "+ maxDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaDeg + "\n";
				ritorno += "\"MAX Gust\": "+ maxGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" "+ contaGust + "\n";
				return ritorno;
			}
			n++;
		}
		
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
	
	
}
	
	
		
	

