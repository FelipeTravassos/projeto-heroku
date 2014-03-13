/**
 * Tests of US1 to US4
 * 
 * @author FELIPE
 */

/*
 * tem testes que poderiam estar em um método para que assim o código ficasse mais limpo e menos repetitivo, mas percebi que ao colocar o asserts 
 * em um metodo para organizar, quando ocorria um redbar ficava mais complicado de saber especificameto onde foi o erro e seu motivo, pois ele não
 * sinaliza o teste e sim a chamada do método.
 */

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import sistema.Disciplina;
import sistema.Plano;
import static org.fest.assertions.Assertions.*;

public class MyTests {

	Plano sistema;		
	
	@Before
	public void setUp(){
		try {
			sistema = new Plano();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * TEST METHODS
	 */
	
	@Test
	public void testUS1() {
        assertThat(sistema.getTotalCredits(1)).isEqualTo(20);
		List<String> disciplinas = sistema.getAllocatedDisciplines(1);
		List<Disciplina> oraculo = getDisciplinesOfFirstPeriodo();
        assertThat(oraculo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < oraculo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getCredits());
		}
	}
	
	@Test
	public void testUS2(){
        assertThat(sistema.getTotalCredits(2)).isEqualTo(0);
		List<Disciplina> oraculo = new ArrayList<Disciplina>();
		try {
			sistema.addDisciplineInPeriod("Calculo 2", 2);
			oraculo.add(new Disciplina("Calculo 2", 4, 4, new String[]{"Calculo 1"}));
			sistema.addDisciplineInPeriod("Discreta", 2);
			oraculo.add(new Disciplina("Discreta", 4, 1));
			sistema.addDisciplineInPeriod("Metodologia", 2);
			oraculo.add(new Disciplina("Metodologia", 4, 1));
			sistema.addDisciplineInPeriod("Prog 2", 2);
			oraculo.add(new Disciplina("Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
			sistema.addDisciplineInPeriod("Lab Prog 2", 2);
			oraculo.add(new Disciplina("Lab Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
			sistema.addDisciplineInPeriod("Grafos", 2);
			oraculo.add(new Disciplina("Grafos", 2, 2, new String[]{"Prog 1", "Lab Prog 1"}));
			sistema.addDisciplineInPeriod("Fisica classica", 2);
			oraculo.add(new Disciplina("Fisica classica", 4, 2, new String[]{"Calculo 1", "Vetorial"}));
			//Disciplina abaixo não sera adicionada por falta de cumprimento dos seus prerequisitos 
			sistema.addDisciplineInPeriod("LEDA", 2);
		} catch (Exception e) {		}
		
        assertThat(26).isEqualTo(sistema.getTotalCredits(2));
		List<String> disciplinas = sistema.getAllocatedDisciplines(2);
        assertThat(oraculo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < oraculo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getCredits());
		}
	}
	
	@Test
	public void testUS3(){
        assertThat(0).isEqualTo(sistema.getTotalCredits(2));
        assertThat(0).isEqualTo(sistema.getTotalCredits(4));
        assertThat(0).isEqualTo(sistema.getTotalCredits(5));
        assertThat(0).isEqualTo(sistema.getTotalCredits(6));
        assertThat(0).isEqualTo(sistema.getTotalCredits(7));
        assertThat(0).isEqualTo(sistema.getTotalCredits(8));
        assertThat(0).isEqualTo(sistema.getTotalCredits(9));
        assertThat(0).isEqualTo(sistema.getTotalCredits(10));
        assertThat(0).isEqualTo(sistema.getTotalCredits(1000));
        		
		List<Disciplina> segundo = new ArrayList<Disciplina>();
		List<Disciplina> terceiro = new ArrayList<Disciplina>();
		List<Disciplina> quarto = new ArrayList<Disciplina>();
		List<Disciplina> quinto = new ArrayList<Disciplina>();
		List<Disciplina> sexto = new ArrayList<Disciplina>();
		List<Disciplina> setimo = new ArrayList<Disciplina>();
		List<Disciplina> oitavo = new ArrayList<Disciplina>();
		List<Disciplina> nono = new ArrayList<Disciplina>();
		List<Disciplina> decimo = new ArrayList<Disciplina>();
		
		try {
			sistema.addDisciplineInPeriod("Calculo 2", 2);
			segundo.add(new Disciplina("Calculo 2", 4, 4, new String[]{"Calculo 1"}));
			sistema.addDisciplineInPeriod("Discreta", 3);
			terceiro.add(new Disciplina("Discreta", 4, 1));
			sistema.addDisciplineInPeriod("Metodologia", 4);
			quarto.add(new Disciplina("Metodologia", 4, 1));
			sistema.addDisciplineInPeriod("Prog 2", 5);
			quinto.add(new Disciplina("Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
			sistema.addDisciplineInPeriod("Lab Prog 2", 6);
			sexto.add(new Disciplina("Lab Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
			sistema.addDisciplineInPeriod("Grafos", 7);
			setimo.add(new Disciplina("Grafos", 2, 2, new String[]{"Prog 1", "Lab Prog 1"}));
			sistema.addDisciplineInPeriod("Fisica classica", 8);
			oitavo.add(new Disciplina("Fisica classica", 4, 2, new String[]{"Calculo 1", "Vetorial"}));
			sistema.addDisciplineInPeriod("LEDA", 9);
			nono.add(new Disciplina("LEDA", 4, 2, new String[]{"Grafos", "Prog 2", "Lab Prog 2"}));
			sistema.addDisciplineInPeriod("EDA", 10);
			decimo.add(new Disciplina("EDA", 4, 2, new String[]{"Grafos", "Prog 2", "Lab Prog 2"}));
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat(4).isEqualTo(sistema.getTotalCredits(2));
        assertThat(4).isEqualTo(sistema.getTotalCredits(4));
        assertThat(4).isEqualTo(sistema.getTotalCredits(5));
        assertThat(4).isEqualTo(sistema.getTotalCredits(6));
        assertThat(2).isEqualTo(sistema.getTotalCredits(7));
        assertThat(4).isEqualTo(sistema.getTotalCredits(8));
        assertThat(4).isEqualTo(sistema.getTotalCredits(9));
        assertThat(4).isEqualTo(sistema.getTotalCredits(10));
        assertThat(0).isEqualTo(sistema.getTotalCredits(1000));
		
		List<String> disciplinas = sistema.getAllocatedDisciplines(2);
		assertThat(segundo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < segundo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(segundo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(segundo.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(3);
		assertThat(terceiro.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < terceiro.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(terceiro.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(terceiro.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(4);
		assertThat(quarto.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < quarto.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(quarto.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(quarto.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(5);
		assertThat(quinto.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < quinto.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(quinto.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(quinto.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(6);
		assertThat(sexto.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < sexto.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(sexto.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(sexto.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(7);
		assertThat(setimo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < setimo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(setimo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(setimo.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(8);
		assertThat(oitavo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < oitavo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(oitavo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(oitavo.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(9);
		assertThat(nono.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < nono.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(nono.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(nono.get(i).getCredits());
		}
		
		disciplinas = sistema.getAllocatedDisciplines(10);
		assertThat(decimo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < decimo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(decimo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(decimo.get(i).getCredits());
		}
	}
	
	@Test
	public void testUS4(){
        assertThat(0).isEqualTo(sistema.getTotalCredits(2));

        List<Disciplina> segundo = new ArrayList<Disciplina>();

		try {
			sistema.addDisciplineInPeriod("Calculo 2", 2);
			segundo.add(new Disciplina("Calculo 2", 4, 3, new String[]{"Calculo 1"}));
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertThat(4).isEqualTo(sistema.getTotalCredits(2));
		
		sistema.removeDisciplineOfPeriod("Calculo 2", 2);
        assertThat(0).isEqualTo(sistema.getTotalCredits(2));

        assertThat(20).isEqualTo(sistema.getTotalCredits(1));
		
		sistema.removeDisciplineOfPeriod("Calculo 1", 2);
        assertThat(20).isEqualTo(sistema.getTotalCredits(1));
		
		try {
			sistema.addDisciplineInPeriod("Prog 2", 2);
			sistema.addDisciplineInPeriod("Calculo 2", 2);
			sistema.addDisciplineInPeriod("Fisica classica", 2);
			sistema.addDisciplineInPeriod("Grafos", 2);
			sistema.addDisciplineInPeriod("Lab Prog 2", 2);
			sistema.addDisciplineInPeriod("EDA", 3);
			sistema.addDisciplineInPeriod("LEDA", 3);
			sistema.addDisciplineInPeriod("Fisica moderna", 3);
			sistema.addDisciplineInPeriod("LOAC", 4);
			sistema.addDisciplineInPeriod("OAC", 4);
			sistema.addDisciplineInPeriod("Rede de computadores", 5);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertThat(18).isEqualTo(sistema.getTotalCredits(2));
        assertThat(12).isEqualTo(sistema.getTotalCredits(3));
        assertThat(8).isEqualTo(sistema.getTotalCredits(4));
        assertThat(4).isEqualTo(sistema.getTotalCredits(5));
        
		sistema.removeDisciplineOfPeriod("Prog 2", 2);

		assertThat(14).isEqualTo(sistema.getTotalCredits(2));
        assertThat(4).isEqualTo(sistema.getTotalCredits(3));
        assertThat(0).isEqualTo(sistema.getTotalCredits(4));
        assertThat(0).isEqualTo(sistema.getTotalCredits(5));
	}

	@Test
	public void testUS5(){
        assertThat(0).isEqualTo(sistema.getDegreeOfDifficulty(2));
	
		try {
			sistema.addDisciplineInPeriod("Calculo 2", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertThat(3).isEqualTo(sistema.getDegreeOfDifficulty(2));
		
        try {
			sistema.addDisciplineInPeriod("Discreta", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        assertThat(4).isEqualTo(sistema.getDegreeOfDifficulty(2));
        
        assertThat(3).isEqualTo(sistema.getDegreeOfDifficultyOfTheDiscipline("Calculo 2"));
	}
	
	/*
	 * Private methods
	 */
	
	private List<Disciplina> getDisciplinesOfFirstPeriodo(){
		List<Disciplina> retorno = new ArrayList<Disciplina>();
		
		retorno.add(new Disciplina("Calculo 1", 4, 3, new String[]{}));
		retorno.add(new Disciplina("IC", 4, 1, new String[]{}));
		retorno.add(new Disciplina("Lab Prog 1", 4, 2, new String[]{}));
		retorno.add(new Disciplina("Prog 1", 4, 2, new String[]{}));
		retorno.add(new Disciplina("Vetorial", 4, 2, new String[]{}));

		return retorno;
	}
}
