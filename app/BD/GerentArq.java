package BD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sistema.Disciplina;

public class GerentArq {
	
	public static void main(String[] args) {
		GerentArq arq = new GerentArq();
		int i = 0;
		for (Disciplina d : arq.getDisciplinasPlanoModificado()) {
			System.out.println(++i +" - "+d.getName());
		}
	}
	
	final int ID = 0, NOME = 1, CREDITOS = 3, DIFICULDADE = 2, PREREQUISITOS = 5, PERIODO = 4;
	final String ARQ_DEFAULT = "files//plano.txt";
	final String ARQ_MODIFICADO = "files//planoModificado.txt";
	final String SEPARADOR = "	";
	
	public List<Disciplina> getDisciplinasDefault(){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		lerArq(ARQ_DEFAULT, disciplinas);
		return disciplinas;
	}
	
	public List<Disciplina> getDisciplinasPlanoModificado(){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		lerArq(ARQ_MODIFICADO, disciplinas);
		return disciplinas;
	}
	
	public void reset(){
		List<Disciplina> disciplinas = getDisciplinasDefault();
		
		FileWriter fw;
		try {
			String text = "";
			for (int i = 0; i < disciplinas.size()-1; i++) {
				text += (disciplinas.get(i).getID()+"	"
						+disciplinas.get(i).getName()+"	"
						+disciplinas.get(i).getDifficulty()+"	"
						+disciplinas.get(i).getCredits()+"	"
						+disciplinas.get(i).getPeriod()+"	"
						+preparaListaPrerequisitos(disciplinas.get(i).getPrerequisites())+"	\n");				
			}

			text += (disciplinas.get(disciplinas.size()-1).getID()+"	"
					+disciplinas.get(disciplinas.size()-1).getName()+"	"
					+disciplinas.get(disciplinas.size()-1).getDifficulty()+"	"
					+disciplinas.get(disciplinas.size()-1).getCredits()+"	"
					+disciplinas.get(disciplinas.size()-1).getPeriod()+"	"
					+preparaListaPrerequisitos(disciplinas.get(disciplinas.size()-1).getPrerequisites())+"	");			
			
			fw = new FileWriter(ARQ_MODIFICADO, false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.newLine();
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlanoModificado(String ID, int fromPeriod){
		List<Disciplina> disciplinas = getDisciplinasPlanoModificado();
		
		FileWriter fw;
		try {
			String text = "";
			for (int i = 0; i < disciplinas.size()-1; i++) {
				text += (disciplinas.get(i).getID()+"	"
						+disciplinas.get(i).getName()+"	"
						+disciplinas.get(i).getDifficulty()+"	"
						+disciplinas.get(i).getCredits()+"	");
				
				if(!disciplinas.get(i).getID().equals(ID))
					text +=	disciplinas.get(i).getPeriod()+"	";
				else
					text +=	fromPeriod+"	";
				
				text +=	preparaListaPrerequisitos(disciplinas.get(i).getPrerequisites())+"	\n";				
			}

			text += (disciplinas.get(disciplinas.size()-1).getID()+"	"
					+disciplinas.get(disciplinas.size()-1).getName()+"	"
					+disciplinas.get(disciplinas.size()-1).getDifficulty()+"	"
					+disciplinas.get(disciplinas.size()-1).getCredits()+"	");
			
					if(!disciplinas.get(disciplinas.size()-1).getID().equals(ID))
						text +=	disciplinas.get(disciplinas.size()-1).getPeriod()+"	";
					else
						text +=	fromPeriod+"	";
					
					text +=	preparaListaPrerequisitos(disciplinas.get(disciplinas.size()-1).getPrerequisites())+"	";			
			
			fw = new FileWriter(ARQ_MODIFICADO, false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.newLine();
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
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
}
