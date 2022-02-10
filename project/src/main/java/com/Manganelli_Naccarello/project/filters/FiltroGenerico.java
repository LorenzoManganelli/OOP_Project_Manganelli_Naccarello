package com.Manganelli_Naccarello.project.filters;

import com.Manganelli_Naccarello.project.service.service;

import java.util.Date;

import com.Manganelli_Naccarello.project.model.*;

public class FiltroGenerico {

    service service = new service();
    private City city = new City();
    private String path = "";
    private Date inizio;
    private Date fine;
    private int n = 0;
    private int contaSpeed = 0;
    private int contaDeg = 0;
    private int contaGust = 0;

    public FiltroGenerico(String cityName, String inizio, String fine) 
    {
        path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
        city = service.leggiFile(path, cityName);
        this.inizio = service.convertiDataOra(inizio);
        this.fine = service.convertiDataOra(fine);
    }

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getContaSpeed() {
		return contaSpeed;
	}

	public void setContaSpeed(int contaSpeed) {
		this.contaSpeed = contaSpeed;
	}

	public int getContaDeg() {
		return contaDeg;
	}

	public void setContaDeg(int contaDeg) {
		this.contaDeg = contaDeg;
	}

	public int getContaGust() {
		return contaGust;
	}

	public void setContaGust(int contaGust) {
		this.contaGust = contaGust;
	}
    

}