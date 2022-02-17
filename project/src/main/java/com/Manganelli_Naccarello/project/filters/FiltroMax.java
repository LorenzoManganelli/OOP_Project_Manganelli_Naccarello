package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.exceptions.*;
import com.Manganelli_Naccarello.project.model.*;

import java.util.Date;

/** Questa classe contiene le informazioni e il metodo propri del filtro per rilevare il valore massimo dei dati in un
 * preciso arco di tempo.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class FiltroMax extends FiltroGenerico {

	private double maxSpeed = 0;
	private String dataMaxSpeed = "";

	private double maxDeg = 0;
	private String dataMaxDeg = "";

	private double maxGust = 0;
	private String dataMaxGust = "";

	public FiltroMax(String cityName, String inizio, String fine) throws FileNotFoundException {
		super(cityName, inizio, fine);
	}

	/** Questo metodo filtra le informazioni contenute in un oggetto city e determina le occorrenze maggiori dei vari 
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
					if (getCity().getSpeedN(getN()) > maxSpeed) {
						maxSpeed = getCity().getSpeedN(getN());
						dataMaxSpeed = getCity().getDataN(getN());
					}
					super.setContaSpeed(super.getContaSpeed()+1);

					if (getCity().getDegN(getN()) > maxDeg) {
						maxDeg = getCity().getDegN(getN());
						dataMaxDeg = getCity().getDataN(getN());
					}
					super.setContaDeg(super.getContaDeg()+1);

					if (getCity().getGustN(getN()) > 0) {
						if (getCity().getGustN(getN()) > maxGust) {
							maxGust = getCity().getGustN(getN());
							dataMaxGust = getCity().getDataN(getN());
						}
						super.setContaGust(super.getContaGust()+1);
					}
					setN(getN() + 1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
				}
				maxSpeed = Math.round(maxSpeed * 100.0) / 100.0;
				maxDeg = Math.round(maxDeg * 100.0) / 100.0;
				maxGust = Math.round(maxGust * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MAX Speed\": " + maxSpeed + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaSpeed() + "\n";
				ritorno += "\"MAX Deg\": " + maxDeg + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaDeg() + "\n";
				ritorno += "\"MAX Gust\": " + maxGust + "\n";
				ritorno += "Ottenuta il giorno: " + dataMaxGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaGust() + "\n";
				return ritorno;
			}
			setN(getN() + 1);
		}
		return "NON SIAMO RIUSCITI AD ANALIZZARE NULLA NELL'INTERVALLO DI TEMPO FORNITO";
	}
}
