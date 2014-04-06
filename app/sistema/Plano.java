/**
 * CONTROLLER: Is the class responsible for control of system
 * HIGH COESSION: Contains listings of period and list of courses offered
 */

package sistema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import BD.GerentArq;

/**
 * @author FELIPE
 */
public class Plano {

	private GerentArq arq = new GerentArq();
	private List<Periodo> listPeriodos;
	private List<Disciplina> listDisciplinasDisponiveis;
	private String userID;
	
	/**
	 * Default constructor
	 * @throws Exception if the total of credits is greater than the maximum value
	 */
	public Plano(String userID) throws Exception {
		listPeriodos = new ArrayList<Periodo>();
		listDisciplinasDisponiveis = new ArrayList<Disciplina>();
		this.userID = userID;
		update();
	}
	
	/**
	 * get total credits
	 * @return total credits for the current period
	 */
	public int getTotalCredits(int period) {
		if(listPeriodos.size() < period) return 0;
		return this.listPeriodos.get(period -1).getTotalCredits();
	}

	/**
	 * get Allocated Disciplines
	 * @return List of allocated disciplines
	 */
	public List<String> getAllocatedDisciplines(int period) {
		if(listPeriodos.size() < period) return new ArrayList<String>();
		return this.listPeriodos.get(period -1).getAllocatedDisciplines();
	}
	
	/**
	 * Remove discipline of period
	 * @param ID: Id of the discipline
	 * @param period: period that will remove the discipline
	 */
	public void removeDisciplineOfPeriod(String ID, int period) {
		listPeriodos.get(period-1).removeDiscipline(ID);
	}
	
	/**
	 * Get Total Periods
	 * @return int: total periods in course
	 */
	public int getTotalPeriods(){
		return listPeriodos.size();
	}

	/**
	 * Get name of discipline
	 * @param string: ID of discipline
	 * @return Name of discipline
	 */
	public String getNameDiscipline(String ID) {
		String name = null;
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(disciplina.getID().equals(ID)){
				name = disciplina.getName();
			}
		}
		return name;
	}

	/**
	 * Get total credits of discipline
	 * @param string: ID of discipline
	 * @return credits of discipline
	 */
	public int getCreditsDiscipline(String ID) {
		int total = 0;
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(disciplina.getID().equals(ID)){
				total = disciplina.getCredits();
			}
		}
		return total;
	}

	/**
	 * get degree of difficulty for specific period
	 * @param period
	 * @return
	 */
	public int getDegreeOfDifficulty(int period) {
		if(listPeriodos.size() >= period && period > 0){
			return listPeriodos.get(period-1).getDegreeOfDifficulty();
		}
		return 0;
	}

	/**
	 * get degree of difficulty of the discipline
	 * @param ID
	 * @return
	 */
	public int getDegreeOfDifficultyOfTheDiscipline(String ID) {
		int difficulty = 0;
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(disciplina.getID().equals(ID)){
				difficulty = disciplina.getDifficulty();
			}
		}
		return difficulty;
	}

	/**
	 * Move the discipline
	 * @param ID of the discipline
	 * @param actualPeriod period actual of discipline
	 * @param fromPeriod period for where it goes discipline
	 * @return 
	 * @throws Exception 
	 */
	public boolean moveDisciplina(String ID, int actualPeriod, int fromPeriod) throws Exception{
		boolean retorno = true;
		if(addForcedDisciplineInPeriod(ID, fromPeriod))
			removeDisciplineOfPeriod(ID, actualPeriod);
		retorno = verifyConsistency(ID, fromPeriod);
		arq.updatePlanoUser(this.userID, ID, fromPeriod);
		//update();
		return retorno;
	}
	
	/**
	 * get max credits of period
	 * @param i
	 * @return max credits of period
	 */
	public int getMaxCreditsOfPeriod(int period) {
		int PERIODO_INVALIDO = -1;
		if(listPeriodos.size() < period)
			return PERIODO_INVALIDO;
		return listPeriodos.get(period-1).getMaxCredits();
	}
	
	/*
	 * Private methods
	 */
	
	public void update() throws Exception {
		loadDisciplinasDisponiveis();
		loadPeriods();
	}

	private void loadPeriods() throws Exception{
		listPeriodos = new ArrayList<Periodo>();
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			addForcedDisciplineInPeriod(disciplina.getID(), disciplina.getPeriod());
		}
	}
	
	private void loadDisciplinasDisponiveis() throws ParserConfigurationException, SAXException, IOException {
		listDisciplinasDisponiveis = new ArrayList<Disciplina>();
		listDisciplinasDisponiveis = arq.getDisciplinesOfPlan(this.userID);
	}
	
	public boolean verifyConsistency(String ID, int period) {
		for (int i = 0; i < period; i++) {
			List<String> disciplinasComPrerequisito = listPeriodos.get(i).getDisciplinesWithPrerequisite(ID);
			if(disciplinasComPrerequisito.size()>0)
				return false;
		}
		return true;
	}

	/**
	 * Add discipline in period, without verify prerequisites 
	 * @param ID: String with ID of discipline
	 * @param period: int period of discipline
	 * @return
	 */
	public boolean addForcedDisciplineInPeriod(String ID, int period) {
		while(listPeriodos.size() < period){
			listPeriodos.add(new Periodo());
			if(listPeriodos.size()>1)
				listPeriodos.get(listPeriodos.size()-2).setMax(28);
		}
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(ID.equals(disciplina.getID())){
				return this.listPeriodos.get(period -1).addDiscipline(disciplina);
			}
		}
		return false;
	}
}