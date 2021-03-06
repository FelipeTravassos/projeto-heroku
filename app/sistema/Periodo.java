/**
 *  INFORMATION EXPERT: the class is responsible for storing disciplines of period
 */

package sistema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FELIPE
 */
public class Periodo {
	
	private List<Disciplina> disciplinas; 
	private int max = 0;
	private int min = 0;
	
	/**
	 * Default constructor
	 */
	public Periodo() {
		disciplinas = new ArrayList<Disciplina>();
	}
	
	/**
	 * Constructor with maximum and minimum values for credits of period
	 */
	public Periodo(int min, int max) {
		disciplinas = new ArrayList<Disciplina>();
		this.max = max;
		this.min = min;
	}

	/**
	 * get maximum value of credits in period
	 * @return maximum value
	 */
	public int getMax() {
		return max;
	}

	/**
	 * set maximum value of credits in period
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * get minimum value of credits in period
	 * @return minimum value
	 */
	public int getMin() {
		return min;
	}

	/**
	 * set minimum value of credits in period
	 * @param min
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * Get total credits of period
	 * @return int: The sum of the credits for the period
	 */
	public int getTotalCredits() {
		int retorno = 0;
		for (Disciplina disciplina : disciplinas) {
			retorno += disciplina.getCredits();
		}
		return retorno;
	}
	
	/**
	 * Get allocated disciplines of period
	 * @return List<String> with the ID the all disciplines allocated in actual period
	 */
	public List<String> getAllocatedDisciplines() {
		List<String> retorno = new ArrayList<String>();
		for (Disciplina disciplina : disciplinas) {
			retorno.add(disciplina.getID());
		}
		return retorno;
	}

	/**
	 * Allocate discipline
	 * @param IDdisciplina: ID of discipline
	 * @param period: Period for allocate the discipline
	 * @return 
	 * @throws Exception if the total of credits is greater than the maximum value
	 */
	public boolean addDiscipline(Disciplina disciplina){
		if(max == 0 || max > 0 && getTotalCredits() + disciplina.getCredits() <= max){
			disciplinas.add(disciplina);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Remove discipline of period
	 * @param ID: ID of discipline
	 */
	public void removeDiscipline(String ID) {
		for (Disciplina disciplina : disciplinas) {
			if(disciplina.getID().equals(ID)){
				disciplinas.remove(disciplina);
				break;
			}
		}
	}

	/**
	 * get disciplines with prerequisite
	 * @param ID: ID of discipline
	 * @return List<String> with ID of disciplines that has the disciplina (ID) as prerequisite
	 */
	public List<String> getDisciplinesWithPrerequisite(String ID) {
		List<String> retorno = new ArrayList<String>();
		
		for (Disciplina disciplina : disciplinas) {
			String[] prerequisitos = disciplina.getPrerequisites();
			for (int i = 0; i < prerequisitos.length; i++) {
				if(prerequisitos[i].equals(ID)){
					retorno.add(disciplina.getID());
					break;
				}
			}
		}
		
		return retorno;
	}

	/**
	 * get degree of difficulty for specific period
	 * @param period
	 * @return
	 */
	public int getDegreeOfDifficulty() {
		int retorno = 0;
		for (Disciplina disciplina : disciplinas) {
			retorno += disciplina.getDifficulty();
		}
		return retorno;
	}

	public int getMaxCredits() {
		return this.max;
	}

}
