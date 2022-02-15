package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.model.WindData;
import com.Manganelli_Naccarello.project.exceptions.*;

import java.util.Date;

public class FiltroMedia extends FiltroGenerico {

	public FiltroMedia(String cityName, String inizio, String fine) throws FileNotFoundException{
		super(cityName, inizio, fine);
	}
	
	private double mediaSpeed = 0;
	private int contaSpeed = 0;

	private double mediaDeg = 0;
	private int contaDeg = 0;

	private double mediaGust = 0;
	private int contaGust = 0;

	public String filtro() {
		for (WindData dato : getCity().getPrevisioni()) {
			Date dataPrevisione = null;
			dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
			int fine = dataPrevisione.compareTo(getFine());
			int confronto = dataPrevisione.compareTo(getInizio());

			if (confronto >= 0) {
				while (fine < 0) {
					mediaSpeed += getCity().getSpeedN(getN());
					contaSpeed++;

					mediaDeg += getCity().getDegN(getN());
					contaDeg++;

					if (getCity().getGustN(getN()) != -1) {
						mediaGust += getCity().getGustN(getN());
						contaGust++;
					}
					setN(getN() + 1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
				}
				mediaSpeed = Math.round(mediaSpeed / contaSpeed * 100.0) / 100.0;
				mediaDeg = Math.round(mediaDeg / contaDeg * 100.0) / 100.0;
				mediaGust = Math.round(mediaGust / contaGust * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MEDIA Speed\": " + mediaSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaSpeed + "\n";
				ritorno += "\"MEDIA Deg\": " + mediaDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaDeg + "\n";
				ritorno += "\"MEDIA Gust\": " + mediaGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + contaGust + "\n";
				return ritorno;
			}
			setN(getN() + 1);
		}
		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
}
