/**
 * CONTROLLER: Is the class responsible for control of system
 * HIGH COESSION: Contains listings of period and list of courses offered
 */

package sistema;

import java.util.ArrayList;
import java.util.List;

import BD.LeitorArquivo;

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
		loadFirstPeriod();
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
		if(listPeriodos.size() >= period && period > 1){
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
	
	/*
	 * Private methods
	 */
	
	private void loadFirstPeriod() throws Exception{
		String[] disciplinas = new String[]{"Calculo 1", "IC", "Lab Prog 1", 
				"Prog 1", "Vetorial"};
		for (int i = 0; i < disciplinas.length; i++) {
			addDisciplineInPeriod(disciplinas[i], 1);
		}
	}
	
	private void loadDisciplinasDisponiveis() {
		LeitorArquivo leitorArq = new LeitorArquivo();
		List<String[]> disciplinas = leitorArq.read("disciplinas.txt");
		int NOME_DISCIPLINA = 0;
		int TOTAL_CREDITOS = 1;
		int GRAU_DIFICULDADE = 2;
		int PREREQUISITOS = 3;
		
		for (String[] disciplina : disciplinas) {
			if(disciplina.length ==4){
				this.listDisciplinasDisponiveis.add
				(new Disciplina(disciplina[NOME_DISCIPLINA], Integer.parseInt(disciplina[TOTAL_CREDITOS]),
						Integer.parseInt(disciplina[GRAU_DIFICULDADE]) ,disciplina[PREREQUISITOS].split("#")));				
			}else{
				this.listDisciplinasDisponiveis.add
				(new Disciplina(disciplina[NOME_DISCIPLINA], Integer.parseInt(disciplina[TOTAL_CREDITOS]), 
						Integer.parseInt(disciplina[GRAU_DIFICULDADE])));
			}
		}
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
}
