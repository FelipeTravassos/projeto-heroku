/**
 * INFORMATION EXPERT: Is the class responsible for storing information such as the name of discipline and total credits
 */

package sistema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FELIPE
 */
public class Disciplina {

	String name;
	int credits;
	String ID;
	int dificult;
	String[] prerequisitos = new String[]{};
	
	/**
	 * Default constructor 
	 */
	public Disciplina() {
	}
	
	/**
	 * Alternate constructor
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
	 * Alternate constructor
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
		this.prerequisitos = prerequisitos;
	}

	/**
	 * get ID of discipline
	 * 
	 * @return
	 */
	public String getID(){
		return this.ID;
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
	 * Modifies the credits of discipline
	 * 
	 * @param credits: New value for credits
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * 
	 * @return: Name of discipline
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return: Credits of discipline
	 */
	public int getCredits() {
		return this.credits;
	}

	/**
	 * get prerequisites
	 * @return list of prerequisites
	 */
	public String[] getPrerequisites() {
		// TODO Auto-generated method stub
		return this.prerequisitos;
	}

	/**
	 * get difficulty
	 * @return
	 */
	public int getDifficulty() {
		return dificult;
	}

}
