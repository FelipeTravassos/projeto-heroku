/**
 * INFORMATION EXPERT: Is the class responsible for storing information such as the name of discipline and total credits
 *
 *
 */

package sistema;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model.Finder;

/**
 * @author FELIPE
 */
public class Disciplina {

	String ID;
	String name;
	int credits;
	int dificult;
    List<String> prerequisitos = new ArrayList<String>();
	int period;
	
	/**
	 * Default constructor 
	 */
	public Disciplina() {
	}
	
	/**
	 * Alternate constructor 1
	 * 
	 * @param name: name of discipline
	 * @param credits: credits of discipline
	 */
	public Disciplina(String name, int credits, int dificult) {
		setName(name);
		setCredits(credits);
		this.ID = name;
		this.dificult = dificult;
	}
	
	/**
	 * Alternate constructor 2
	 * 
	 * @param name: name of discipline
	 * @param credits: credits of discipline
	 * @param prerequisitos: Prerequisites
	 */
	public Disciplina(String name, int credits, int dificult, String[] prerequisitos) {
		setName(name);
		setCredits(credits);
		this.ID = name;
		this.dificult = dificult;
		for (int i = 0; i < prerequisitos.length; i++) {
			this.prerequisitos.add(prerequisitos[i]);
		}
	}

	/*
	 * Getters and Setters
	 */
	
	/**
	 * get ID of discipline
	 * 
	 * @return String with ID
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * set ID of discipline
	 * @param ID
	 */
	public void setID(String ID){
		this.ID = ID;
	}

	/**
	 * Modifies the name of discipline
	 * 
	 * @param name: New name of discipline
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return: String with Name of discipline
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Modifies the credits of discipline
	 * 
	 * @param credits: New value for credits
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * 
	 * @return: Int object with Credits of discipline
	 */
	public int getCredits() {
		return this.credits;
	}

	/**
	 * get prerequisites
	 * @return String[]: list of prerequisites
	 */
	public String[] getPrerequisites() {
		String[] retorno = new String[prerequisitos.size()];
		for (int i = 0; i < retorno.length; i++) {
			retorno[i] = prerequisitos.get(i);
		}
		return retorno;
	}
	
	/**
	 * set prerequisites for the discipline
	 * @param prerequisitos
	 */
	public void setPrerequisitos(String[] prerequisitos) {
		this.prerequisitos = new ArrayList<String>();
		for (int i = 0; i < prerequisitos.length; i++) {
			this.prerequisitos.add(prerequisitos[i]);
		}
	}

	/**
	 * get difficulty
	 * @return int with difficulty
	 */
	public int getDifficulty() {
		return dificult;
	}
	
	/**
	 * set dificult of discipline
	 * @param dificult
	 */
	public void setDificult(int dificult) {
		this.dificult = dificult;
	}

	/**
	 * get period of discipline
	 * @return int with period
	 */
	public int getPeriod() {
		return period;
	}
	
	/**
	 * set period of discipline
	 * @param period
	 */
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public static Finder<String,Disciplina> find = new Finder<String,Disciplina>(
	        String.class, Disciplina.class
	    );
}
