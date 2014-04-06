package BD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sistema.Disciplina;
import sistema.User;

public class GerentArq {
		
	final int ID = 0, NOME = 1, CREDITOS = 3, DIFICULDADE = 2, PREREQUISITOS = 5, PERIODO = 4;
	final String ARQ_DEFAULT = "files//plano.txt";
	final String USERS = "files//users.txt";
	final String SEPARADOR = "	";
	
	/**
	 * get default plan
	 * @return List<Disciplina> with default plan
	 */
	public List<Disciplina> getDisciplinasDefault(){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		lerArq(ARQ_DEFAULT, disciplinas);
		return disciplinas;
	}
	
	/**
	 * Return disciplines of plan the user
	 * @param ID: ID of user
	 * @return List of disciplines
	 */
	public List<Disciplina> getDisciplinesOfPlan(String ID){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		lerArq(getPathForSavePlan(ID), disciplinas);
		return disciplinas;
	}
	
	/**
	 * Prepare path for file the plan of user
	 * @param email: String - email of user
	 * @return String with path
	 */
	private String getPathForSavePlan(String email){
		return "files//plan-"+email+".txt";
	}
	
	/**
	 * Add new user with default plan
	 * @param email
	 * @param password
	 * @param nome
	 * @return
	 */
	public boolean addUser(String email, String password, String nome){
		boolean retorno = false;
		if(!isUser(email)){
			FileWriter fw;	
			try {
				String add = email+SEPARADOR+
						password+SEPARADOR+
						nome+SEPARADOR+
						getPathForSavePlan(email)+"\n";
				fw = new FileWriter(USERS, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(add);
				bw.close();
				fw.close();
				reset(email);
				retorno = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	/**
	 * Is user
	 * @param email: String with email/ID of user
	 * @param password: String with password of user
	 * @return true if is user or false if not is user 
	 */
	public boolean isUser(String email, String password){
		FileReader fileReader;
		try {
			fileReader = new FileReader(USERS);
			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			while((data = reader.readLine()) != null){
				if(data.split(SEPARADOR)[0].equals(email) && data.split(SEPARADOR)[1].equals(password)){
					fileReader.close();
					reader.close();
					return true;
				}
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Is user
	 * @param email: String with email for verify if is user
	 * @return true if user or false without user
	 */
	private boolean isUser(String email){
		FileReader fileReader;
		try {
			fileReader = new FileReader(USERS);
			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			while((data = reader.readLine()) != null){
				if(data.split("	")[0].equals(email)){
					fileReader.close();
					reader.close();
					return true;
				}
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Get User
	 * @param email
	 * @param password
	 * @return User
	 */
	public User getUser(String email, String password){ 
		FileReader fileReader;
		try {
			fileReader = new FileReader(USERS);
			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			while((data = reader.readLine()) != null){
				if(data.split(SEPARADOR)[0].equals(email) && data.split(SEPARADOR)[1].equals(password)){
					String[] dados = data.split("	");
					return new User(dados[0], dados[1], dados[2]);
				}
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Reset plan of user
	 * @param userID: String - Email of user
	 */
	public void reset(String userID){
		List<Disciplina> disciplinas = getDisciplinasDefault();
		
		FileWriter fw;
		try {
			String text = "";
			for (int i = 0; i < disciplinas.size()-1; i++) {
				text += (disciplinas.get(i).getID()+SEPARADOR
						+disciplinas.get(i).getName()+SEPARADOR
						+disciplinas.get(i).getDifficulty()+SEPARADOR
						+disciplinas.get(i).getCredits()+SEPARADOR
						+disciplinas.get(i).getPeriod()+SEPARADOR
						+preparaListaPrerequisitos(disciplinas.get(i).getPrerequisites())+"	\n");				
			}

			text += (disciplinas.get(disciplinas.size()-1).getID()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getName()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getDifficulty()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getCredits()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getPeriod()+SEPARADOR
					+preparaListaPrerequisitos(disciplinas.get(disciplinas.size()-1).getPrerequisites())+SEPARADOR);			
			
			fw = new FileWriter(getPathForSavePlan(userID), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.newLine();
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * User upgrade plan
	 * @param email
	 * @param disciplineID
	 * @param fromPeriod
	 */
	public void updatePlanoUser(String email, String disciplineID, int fromPeriod) {
		List<Disciplina> disciplinas = getDisciplinesOfPlan(email);
		FileWriter fw;
		try {
			String text = "";
			for (int i = 0; i < disciplinas.size()-1; i++) {
				text += (disciplinas.get(i).getID()+SEPARADOR
						+disciplinas.get(i).getName()+SEPARADOR
						+disciplinas.get(i).getDifficulty()+SEPARADOR
						+disciplinas.get(i).getCredits()+SEPARADOR);
				
				if(!disciplinas.get(i).getID().equals(disciplineID))
					text +=	disciplinas.get(i).getPeriod()+SEPARADOR;
				else
					text +=	fromPeriod+SEPARADOR;
				
				text +=	preparaListaPrerequisitos(disciplinas.get(i).getPrerequisites())+"	\n";				
			}

			text += (disciplinas.get(disciplinas.size()-1).getID()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getName()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getDifficulty()+SEPARADOR
					+disciplinas.get(disciplinas.size()-1).getCredits()+SEPARADOR);
			
					if(!disciplinas.get(disciplinas.size()-1).getID().equals(disciplineID))
						text +=	disciplinas.get(disciplinas.size()-1).getPeriod()+SEPARADOR;
					else
						text +=	fromPeriod+SEPARADOR;
					
					text +=	preparaListaPrerequisitos(disciplinas.get(disciplinas.size()-1).getPrerequisites())+SEPARADOR;			
			
			fw = new FileWriter(getPathForSavePlan(email), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.newLine();
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * prepare list with prerequisites for save in file
	 * @param prerequisites
	 * @return String with prerequisites
	 */
	private String preparaListaPrerequisitos(String[] prerequisites) {
		if(prerequisites.length ==0)
			return "";
		String retorno = "{";
		
		for (int i = 0; i < prerequisites.length-1; i++) {
			retorno += prerequisites[i];
		}
		retorno += prerequisites[prerequisites.length -1];
		retorno += "}";		
		return retorno;
	}

	/**
	 * Read file
	 * @param arq: String with path of file for read
	 * @param disc: List<Disciplina> for add disciplines of file
	 */
	public void lerArq(String arq, List<Disciplina> disc){
		FileReader fileReader;
		try {
			fileReader = new FileReader(arq);
			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			while((data = reader.readLine()) != null){
		 		//ID	nome	creditos	dificudade	prerequisitos	periodo
				String[] dados = data.split("	");
				Disciplina disciplina = null;
				if(dados.length == 6)
					disciplina = new Disciplina(dados[NOME], Integer.parseInt(dados[CREDITOS]), 
			    		Integer.parseInt(dados[DIFICULDADE]), dados[PREREQUISITOS].replace("{", "")
			    		.replace("}", "").split(";"));
				else if(dados.length > 0)
					disciplina = new Disciplina(dados[NOME], Integer.parseInt(dados[CREDITOS]), 
				    		Integer.parseInt(dados[DIFICULDADE]));
				
				disciplina.setPeriod(Integer.parseInt(dados[PERIODO]));
			    disciplina.setID(dados[ID]);
			    disc.add(disciplina);
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeUser(String id2, String password) {
		// TODO Auto-generated method stub
		
	}

	
}
