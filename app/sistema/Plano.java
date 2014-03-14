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

import BD.xmlParser;

/**
 * @author FELIPE
 */
public class Plano {

	List<Periodo> listPeriodos;
	List<Disciplina> listDisciplinasDisponiveis;
	List<Disciplina> listDisciplinasAlocadas;
	
	/**
	 * Default constructor
	 * @throws Exception if the total of credits is greater than the maximum value
	 */
	public Plano() throws Exception {
		listPeriodos = new ArrayList<Periodo>();
		listDisciplinasAlocadas = new ArrayList<Disciplina>();
		listDisciplinasDisponiveis = new ArrayList<Disciplina>();
		loadDisciplinasDisponiveis();
		loadPeriods();
	}

	/**
	 * get periods
	 * @return List<Integer> with numbers of periods
	 */
	public List<Integer> getPeriodos(){
		List<Integer> periodos = new ArrayList<Integer>();
		for (int i = 1; i <= listPeriodos.size(); i++) {
			periodos.add(i);
		}
		return periodos;
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
	 * Allocate discipline
	 * @param IDdisciplina: ID of discipline
	 * @param period: Period for allocate the discipline
	 * @throws Exception if the total of credits is greater than the maximum value
	 */
	public void addDisciplineInPeriod(String ID, int period) throws Exception{
		while(listPeriodos.size() < period){
			listPeriodos.add(new Periodo());
			if(listPeriodos.size()>1)
				listPeriodos.get(listPeriodos.size()-2).setMax(28);
		}
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(ID.equals(disciplina.getID())){
				if(!verifyValidatorPrerequisites(disciplina.getPrerequisites(), period)){
					throw new Exception("Nao atende pre requisito");
				}
				this.listPeriodos.get(period -1).addDiscipline(disciplina);
				this.listDisciplinasAlocadas.add(disciplina);
			}
		}
		
	}

	/**
	 * 
	 * @param ID: Id of the discipline
	 * @param period: period that will remove the discipline
	 */
	public void removeDisciplineOfPeriod(String ID, int period) {
		if(listPeriodos.size() >= period && period > 0){
			listPeriodos.get(period-1).removeDiscipline(ID);
			removeDisciplineWithThisPrerequisites(ID, period);
		}
	}
	
	/**
	 * get All Disciplines
	 * @return List with ID of all disciplines
	 */
	public List<String> getAllDisciplines(){
		List<String> retorno= new ArrayList<String>();
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			retorno.add(disciplina.getID());
		}
		return retorno;
	}

	/**
	 * 
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
	 * 
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
	public boolean moveDisciplina(String ID, int actualPeriod, int fromPeriod) throws Exception {
		boolean retorno = true;
		
		listPeriodos.get(actualPeriod-1).removeDiscipline(ID);
		addForcedDisciplineInPeriod(ID, fromPeriod);
		retorno = verifyConsistency(ID, fromPeriod);
		
		return retorno;
	}
	
	/**
	 * get max credits of period
	 * @param i
	 * @return max credits of period
	 */
	public int getMaxCreditsOfPeriod(int i) {
		int PERIODO_INVALIDO = -1;
		if(listPeriodos.size() < i)
			return PERIODO_INVALIDO;
		return listPeriodos.get(i-1).getMaxCredits();
	}
	
	/*
	 * Private methods
	 */
	
	private void loadPeriods() throws Exception{
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			addDisciplineInPeriod(disciplina.getID(), disciplina.getPeriod());
		}
	}
	
	private void loadDisciplinasDisponiveis() throws ParserConfigurationException, SAXException, IOException {
		xmlParser parser = new xmlParser();
		listDisciplinasDisponiveis = parser.getListOfDiscipline();
	}

	private boolean verifyValidatorPrerequisites(String[] prerequisites, int periodoLimite) {
		
		for (int i = 0; i < prerequisites.length; i++) {
			if(!searchDisciplineInPeriod(prerequisites[i], periodoLimite)){ 
				return false;
			}
		}
		return true;
	}

	private boolean searchDisciplineInPeriod(String ID, int periodoLimite) {
		for (int i = 0; i < periodoLimite -1; i++) {
			List<String> disciplinas = listPeriodos.get(i).getAllocatedDisciplines();
			for (int j = 0; j < disciplinas.size(); j++) {
				if(disciplinas.get(j).equals(ID)){
					return true;
				}
			}
			
		}
		return false;
	}
	
	private void removeDisciplineWithThisPrerequisites(String ID, int period) {
		if(listPeriodos.size() > period){
			List<String> disciplinasComPrerequisito = listPeriodos.get(period).getDisciplinesWithPrerequisite(ID);
			for (String disciplina : disciplinasComPrerequisito) {
				removeDisciplineOfPeriod(disciplina, period+1);
			}
			removeDisciplineWithThisPrerequisites(ID, ++period);
		}
	}


	private boolean verifyConsistency(String ID, int period) {
		for (int i = 0; i < period; i++) {
			List<String> disciplinasComPrerequisito = listPeriodos.get(i).getDisciplinesWithPrerequisite(ID);
			if(disciplinasComPrerequisito.size()>0)
				return false;
		}
		return true;
	}

	private void addForcedDisciplineInPeriod(String ID, int period) throws Exception {
		while(listPeriodos.size() < period){
			listPeriodos.add(new Periodo());
		}
		for (Disciplina disciplina : listDisciplinasDisponiveis) {
			if(ID.equals(disciplina.getID())){
				this.listPeriodos.get(period -1).addDiscipline(disciplina);
			}
		}
	}
}
