package com.Manganelli_Naccarello.project.filters;

import java.util.Date;

import com.Manganelli_Naccarello.project.model.*;

public class FiltroMin extends FiltroGenerico {
	private double minSpeed = 999999;
	private double minDeg = 999999;
	private double minGust = 999999;

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
				double minSpeed = 9999999;
				String dataMinSpeed = "";
				int contaSpeed = 0;
				double minDeg = 9999999;
				String dataMinDeg = "";
				int contaDeg = 0;
				double minGust = 9999999;
				String dataMinGust = "";
				int contaGust = 0;

				while (fine < 0) {

					if (getCity().getSpeedN(getN()) < minSpeed) {
						minSpeed = getCity().getSpeedN(getN());
						dataMinSpeed = getCity().getDataN(getN());
					}
					contaSpeed++;

					if (getCity().getDegN(getN()) < minDeg) {
						minDeg = getCity().getDegN(getN());
						dataMinDeg = getCity().getDataN(getN());
					}
					contaDeg++;

					if (getCity().getGustN(getN()) < minGust) {
						if (getCity().getGustN(getN()) > 0) {
							minGust += getCity().getGustN(getN());
							dataMinGust = getCity().getDataN(getN());
						}
					}
					contaGust++;
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
}
