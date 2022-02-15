package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.exceptions.*;
import com.Manganelli_Naccarello.project.model.*;

import java.util.Date;

public class FiltroMax extends FiltroGenerico {

	private double maxSpeed = 0;
	private String dataMaxSpeed = "";
	private int contaSpeed = 0;

	private double maxDeg = 0;
	private String dataMaxDeg = "";
	private int contaDeg = 0;

	private double maxGust = 0;
	private String dataMaxGust = "";
	private int contaGust = 0;

	public FiltroMax(String cityName, String inizio, String fine) throws FileNotFoundException {
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
					if (getCity().getSpeedN(getN()) > maxSpeed) {
						maxSpeed = getCity().getSpeedN(getN());
						dataMaxSpeed = getCity().getDataN(getN());
					}
					contaSpeed++;

					if (getCity().getDegN(getN()) > maxDeg) {
						maxDeg = getCity().getDegN(getN());
						dataMaxDeg = getCity().getDataN(getN());
					}
					contaDeg++;

					if (getCity().getGustN(getN()) > 0) {
						if (getCity().getGustN(getN()) > maxGust) {
							maxGust = getCity().getGustN(getN());
							dataMaxGust = getCity().getDataN(getN());
						}
						contaGust++;
					}
					setN(getN() + 1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
				}
				maxSpeed = Math.round(maxSpeed * 100.0) / 100.0;
				maxDeg = Math.round(maxDeg * 100.0) / 100.0;
				maxGust = Math.round(maxGust * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MIN Speed\": " + maxSpeed + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaSpeed + "\n";
				ritorno += "\"MIN Deg\": " + maxDeg + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaDeg + "\n";
				ritorno += "\"MIN Gust\": " + maxGust + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaGust + "\n";
				return ritorno;
			}
			setN(getN() + 1);
		}
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
}
