package com.Manganelli_Naccarello.project.filters;

import java.util.Date;

import com.Manganelli_Naccarello.project.model.*;

public class FiltroMin extends FiltroGenerico {
	
	private double minSpeed = 999999;
	private String dataMinSpeed = "";
	private int contaSpeed = 0;
	
	private double minDeg = 999999;
	private String dataMinDeg = "";
	private int contaDeg = 0;
	
	private double minGust = 999999;
	private String dataMinGust = "";
	private int contaGust = 0;

	public FiltroMin(String cityName, String inizio, String fine) {
		super(cityName, inizio, fine);

	}

	public String filtro() {
		for (WindData dato : getCity().getPrevisioni()) {
			Date dataPrevisione = null;
			dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
			int fine = dataPrevisione.compareTo(getFine());
			int confronto = dataPrevisione.compareTo(getInizio());

			if (confronto >= 0) {
				while (fine < 0) {

					if (getCity().getSpeedN(getN()) < minSpeed) {
						System.out.println("speed letta "+ getCity().getSpeedN(getN()));
						minSpeed = getCity().getSpeedN(getN());
						System.out.println("speed minima "+ minSpeed);
						dataMinSpeed = getCity().getDataN(getN());
						System.out.println("data"+ dataMinSpeed);
					}
					contaSpeed++;

					if (getCity().getDegN(getN()) < minDeg) {
						minDeg = getCity().getDegN(getN());
						dataMinDeg = getCity().getDataN(getN());
					}
					contaDeg++;
					
					if (getCity().getGustN(getN()) > 0) {
						if (getCity().getGustN(getN()) < minGust) {
							minGust = getCity().getGustN(getN());
							dataMinGust = getCity().getDataN(getN());
						}
						contaGust++;
					}
					setN(getN()+1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
				}
				minSpeed = Math.round(minSpeed * 100.0) / 100.0;
				minDeg = Math.round(minDeg * 100.0) / 100.0;
				minGust = Math.round(minGust * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MIN Speed\": " + minSpeed + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaSpeed + "\n";
				ritorno += "\"MIN Deg\": " + minDeg + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaDeg + "\n";
				ritorno += "\"MIN Gust\": " + minGust + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaGust + "\n";
				return ritorno;
			}
			setN(getN()+1);
		}
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
	
	
	
	/*
	public String filtroMin (String cityName, String dataOraInizio, String dataOraFine){
		String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		City city = new City();
		city = service.leggiFile(path, cityName);
		Date dataInizio = service.convertiDataOra(dataOraInizio);
		System.out.println(dataInizio);
		Date dataFine = service.convertiDataOra(dataOraFine);
		System.out.println(dataFine);
		int n = 0;
		
		for (WindData dato: city.getPrevisioni()) {
			System.out.println(n);
			Date dataPrevisione = null;
			dataPrevisione = service.convertiDataOra(city.getDataN(n));
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
					System.out.println("speed letta "+city.getSpeedN(n));
					contaSpeed++;
					
					if (city.getDegN(n)<minDeg) {
						minDeg = city.getDegN(n);
						dataMinDeg = city.getDataN(n);
					}
					System.out.println("deg letta "+city.getDegN(n));
					contaDeg++;
					
					if (city.getGustN(n)<minGust) {
						if(city.getGustN(n)>0) {
							minGust = city.getGustN(n);
							System.out.println("min gust "+minGust);
							dataMinGust = city.getDataN(n);
						}
					}
					System.out.println("gust letto"+city.getGustN(n));
					contaGust++;
					n++;
					dataPrevisione = service.convertiDataOra(city.getDataN(n));
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
*/
	
	
}


