package com.Manganelli_Naccarello.project.model;

import java.util.Vector;

import com.Manganelli_Naccarello.project.model.WindData;

public class City
{
	private String name;
	private Vector <WindData> previsioni = new Vector<WindData>();
	
		
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void addPrevisioni (WindData prev) {
		previsioni.add(prev);
	}
	
	public Vector<WindData> getPrevisioni () {
		return previsioni;
	}
	
	public String getDataN(int n) {
		return previsioni.elementAt(n).getDataOra();
	}
	
	public double getSpeedN(int n) {
		return previsioni.elementAt(n).getWindSpeed();
	}
	
	public double getDegN(int n) {
		return previsioni.elementAt(n).getWindDeg();
	}
	
	public double getGustN(int n) {
		return previsioni.elementAt(n).getWindGust();
	}
	
	
	public void printVector () {
		for (WindData dato: previsioni) dato.printPrevisioni();
	}
	
	public String toString(){
		String ritorno = "Città = "+this.name+"\n";
		for (WindData dato: previsioni) ritorno += dato.toString();
		return ritorno;
		
	}	
}
