package controllers;

public class Auxiliar {

	public String concatenaCaminho(String caminho, String disc, int periodo) {
		return (caminho+disc+"/"+periodo).replace(" ", "_");
	}
	
}
