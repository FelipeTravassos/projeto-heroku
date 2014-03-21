package controllers;

public class Auxiliar {

	public String concatenaCaminho(String caminho, String disc, int periodo, int actualPeriod) {
		return (caminho+disc+"/"+periodo+"/"+actualPeriod).replace(" ", "_");
	}
	
}
