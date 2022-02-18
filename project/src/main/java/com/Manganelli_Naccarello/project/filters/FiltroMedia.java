package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.model.WindData;
import com.Manganelli_Naccarello.project.exceptions.*;

import java.util.Date;

/** Questa classe contiene le informazioni e il metodo propri del filtro per rilevare il valore minore dei dati in un
 * preciso arco di tempo.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */

public class FiltroMedia extends FiltroGenerico {

	public FiltroMedia(String cityName, String inizio, String fine) throws FileNotFoundException{
		super(cityName, inizio, fine);
	}
	
	private double mediaSpeed = 0;
	private double mediaDeg = 0;
	private double mediaGust = 0;

	/** Questo metodo filtra le informazioni contenute in un oggetto city e calcola il valore medio dei vari parametri letti.
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
					mediaSpeed += getCity().getSpeedN(getN());
					super.setContaSpeed(super.getContaSpeed()+1);

					mediaDeg += getCity().getDegN(getN());
					super.setContaDeg(super.getContaDeg()+1);

					if (getCity().getGustN(getN()) != -1) {
						mediaGust += getCity().getGustN(getN());
						super.setContaGust(super.getContaGust()+1);
					}
					setN(getN() + 1);
					dataPrevisione = service.convertiDataOra(getCity().getDataN(getN()));
					fine = dataPrevisione.compareTo(getFine());
					if (getN() == getCity().getPrevisioni().size()-1) break;
				}
				mediaSpeed = Math.round(mediaSpeed / super.getContaSpeed() * 100.0) / 100.0;
				mediaDeg = Math.round(mediaDeg / super.getContaDeg() * 100.0) / 100.0;
				mediaGust = Math.round(mediaGust / super.getContaGust() * 100.0) / 100.0;

				String ritorno = "";
				ritorno += "\"MEDIA Speed\": " + mediaSpeed + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaSpeed() + "\n";
				ritorno += "\"MEDIA Deg\": " + mediaDeg + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaDeg() + "\n";
				ritorno += "\"MEDIA Gust\": " + mediaGust + "\n";
				ritorno += "\"NUMERO DATI ESAMINATI:\" " + super.getContaGust() + "\n";
				return ritorno;
			}
			setN(getN() + 1);
		}
		return "NON SIAMO RIUSCITI AD ANALIZZARE NULLA NELL'INTERVALLO DI TEMPO FORNITO";
	}
}
