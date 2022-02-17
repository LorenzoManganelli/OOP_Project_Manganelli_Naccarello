package com.Manganelli_Naccarello.project.model;

/** Questa classe rappresenta le previsioni del vento prese in un determinato momento della giornata.
 * 
 * @author Raffaele
 * @author Lorenzo
 * */


public class WindData 
{
	private String dataOra;
	private double windSpeed;
	private double windDeg;
	private double windGust;
	
	public String getDataOra() {
		return dataOra;
	}
	public void setDataOra(String dataOra) {
		this.dataOra = dataOra;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getWindDeg() {
		return windDeg;
	}
	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}
	public double getWindGust() {
		return windGust;
	}
	public void setWindGust(double windGust) {
		this.windGust = windGust;
	}
	
	public void printPrevisioni () {
		System.out.println("DATE: "+ dataOra);
		System.out.println("SPEED: "+ windSpeed);
		System.out.println("DEG: "+ windDeg);
		System.out.println("GUST: "+ windGust);
		System.out.println("");
	}
	
	public String toString() {
		String ritorno = "Date = "+ this.dataOra+ "\n";
		ritorno += "Speed = "+ this.windSpeed+ "\n";
		ritorno += "Deg = "+ this.windDeg+ "\n";
		ritorno += "Gust = "+ this.windGust+ "\n\n";
		return ritorno;
	}
}