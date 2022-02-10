package com.Manganelli_Naccarello.project.filters;

import java.util.Date;

import com.Manganelli_Naccarello.project.model.City;
import com.Manganelli_Naccarello.project.model.WindData;

public class FiltroMed extends FiltroGenerico {

	public FiltroMed(String cityName, String inizio, String fine) {
		super(cityName, inizio, fine);
	}

	public String filtro() {
		for (WindData dato : getCity().getPrevisioni()) {
			Date dataPrevisione = null;
			dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
			int fine = dataPrevisione.compareTo(getFine());
			int confronto = dataPrevisione.compareTo(getInizio());
			setN(getN()+1);
			if (confronto >= 0) {
				double mediaSpeed = 0;
				int contaSpeed = 0;
				double mediaDeg = 0;
				int contaDeg = 0;
				double mediaGust = 0;
				int contaGust = 0;

				while (fine < 0) {
					mediaSpeed += getCity().getSpeedN(getN());
					System.out.println(mediaSpeed);
					contaSpeed++;
					mediaDeg += getCity().getDegN(getN());
					System.out.println(mediaDeg);
					contaDeg++;

					if (getCity().getGustN(getN()) != -1) {
						mediaGust += getCity().getGustN(getN());
						System.out.println(mediaGust);
						contaGust++;
					}
					setN(getN()+1);
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
			setN(getN()+1);
			System.out.println("Non trovato");
		}

		return "Non siamo riusciti ad analizzare nulla nell'intervallo di tempo che ci hai dato";
	}
}
