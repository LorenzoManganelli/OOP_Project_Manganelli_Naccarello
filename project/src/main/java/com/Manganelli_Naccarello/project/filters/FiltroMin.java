package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.model.*;
import com.Manganelli_Naccarello.project.exceptions.*;

import java.util.Date;

/** Questa classe contiene le informazioni e il metodo propri del filtro per rilevare il valore minore dei dati in un
 * preciso arco di tempo.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class FiltroMin extends FiltroGenerico {
	
	private double minSpeed = 999999;
	private String dataMinSpeed = "";

	private double minDeg = 999999;
	private String dataMinDeg = "";

	private double minGust = 999999;
	private String dataMinGust = "";

	public FiltroMin(String cityName, String inizio, String fine) throws FileNotFoundException {
		super(cityName, inizio, fine);
	}
	
	/** Questo metodo filtra le informazioni contenute in un oggetto city e determina le occorrenze minori dei vari 
	 * parametri letti.
	 * 
	 * @return una stringa contenenente tutte le informazioni filtrate se il filtraggio è andato a buon fine.
	 * @return un messaggio predefinitto se il filtraggio non è andato a buon fine.
	 * */
	public String filtro() {
		for (WindData dato : getCity().getPrevisioni()) {
			Date dataPrevisione = null;
			dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
			int fine = dataPrevisione.compareTo(getFine());
			int confronto = dataPrevisione.compareTo(getInizio());

			if (confronto >= 0) {
				while (fine < 0) {
					if (getCity().getSpeedN(getN()) < minSpeed) {
						minSpeed = getCity().getSpeedN(getN());
						dataMinSpeed = getCity().getDataN(getN());
					}
					super.setContaSpeed(super.getContaSpeed()+1);

					if (getCity().getDegN(getN()) < minDeg) {
						minDeg = getCity().getDegN(getN());
						dataMinDeg = getCity().getDataN(getN());
					}
					super.setContaDeg(super.getContaDeg()+1);

					if (getCity().getGustN(getN()) > 0) {
						if (getCity().getGustN(getN()) < minGust) {
							minGust = getCity().getGustN(getN());
							dataMinGust = getCity().getDataN(getN());
						}
						super.setContaGust(super.getContaGust()+1);
					}
					setN(getN() + 1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
				}
				minSpeed = Math.round(minSpeed * 100.0) / 100.0;
				minDeg = Math.round(minDeg * 100.0) / 100.0;
				minGust = Math.round(minGust * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MIN Speed\": " + minSpeed + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaSpeed() + "\n";
				ritorno += "\"MIN Deg\": " + minDeg + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaDeg() + "\n";
				ritorno += "\"MIN Gust\": " + minGust + "\n";
				ritorno += "Ottenuta il giorno: " + dataMinGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaGust() + "\n";
				return ritorno;
			}
			setN(getN() + 1);
		}
		return "NON SIAMO RIUSCITI AD ANALIZZARE NULLA NELL'INTERVALLO DI TEMPO FORNITO";
	}

}