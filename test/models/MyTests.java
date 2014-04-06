package models;
/**
 * Tests for project 
 * 
 * @author FELIPE
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

import BD.GerentArq;
import sistema.Disciplina;
import sistema.Plano;
import sistema.Sistema;
import sistema.User;
import static org.fest.assertions.Assertions.*;

public class MyTests {

	Sistema sistema;		
	GerentArq arq = new GerentArq();

	@Before
	public void setUp(){
		try {
			arq.addUser("admin@email.com", "admin", "admin");
			sistema = new Sistema();
			sistema.login("admin@email.com", "admin");
			sistema.resetPlan();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * TEST METHODS
	 */
	
	@Test
	public void verificaPrimeiroPeriodo() {
        assertThat(sistema.getTotalCredits(1)).isEqualTo(24);
		List<String> disciplinas = sistema.getAllocatedDisciplines(1);
		List<Disciplina> oraculo = new ArrayList<Disciplina>();
		
		oraculo.add(new Disciplina("LPT", 4, 2, new String[]{}));
		oraculo.add(new Disciplina("Calculo 1", 4, 3, new String[]{}));
		oraculo.add(new Disciplina("IC", 4, 1, new String[]{}));
		oraculo.add(new Disciplina("Lab Prog 1", 4, 2, new String[]{}));
		oraculo.add(new Disciplina("Prog 1", 4, 2, new String[]{}));
		oraculo.add(new Disciplina("Vetorial", 4, 2, new String[]{}));
		
        assertThat(oraculo.size()).isEqualTo(disciplinas.size());
		for (int i = 0; i < oraculo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getCredits());
		}
	}
	
	@Test
	public void verificaSegundoPeriodo(){
        assertThat(sistema.getTotalCredits(2)).isEqualTo(28);
		List<Disciplina> oraculo = new ArrayList<Disciplina>();

		oraculo.add(new Disciplina("Calculo 2", 4, 4, new String[]{"Calculo 1"}));
		oraculo.add(new Disciplina("Discreta", 4, 1));
		oraculo.add(new Disciplina("Metodologia", 4, 1));
		oraculo.add(new Disciplina("Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
		oraculo.add(new Disciplina("Grafos", 2, 2, new String[]{"Prog 1", "Lab Prog 1"}));
		oraculo.add(new Disciplina("Fisica classica", 4, 2, new String[]{"Calculo 1", "Vetorial"}));
		oraculo.add(new Disciplina("Lab Prog 2", 4, 2, new String[]{"Prog 1", "Lab Prog 1", "IC"}));
		oraculo.add(new Disciplina("Didatica 1", 2, 1));
		
        assertThat(28).isEqualTo(sistema.getTotalCredits(2));
		List<String> disciplinas = sistema.getAllocatedDisciplines(2);
        assertThat(oraculo.size()).isEqualTo(disciplinas.size());
  
		for (int i = 0; i < oraculo.size(); i++) {
	        assertThat(sistema.getNameDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getName());
	        assertThat(sistema.getCreditsDiscipline(disciplinas.get(i))).isEqualTo(oraculo.get(i).getCredits());
		}
	}
	
	@Test
	public void verificaAcaoDeRemover(){
        assertThat(28).isEqualTo(sistema.getTotalCredits(2));
        
		sistema.removeDisciplineOfPeriod("Prog 2", 2);

		assertThat(24).isEqualTo(sistema.getTotalCredits(2));
	}

	@Test
	public void testGrauDeDificudadeDaDisciplinaEPeriodo(){
        assertThat(15).isEqualTo(sistema.getDegreeOfDifficulty(2));
	
        assertThat(1).isEqualTo(sistema.getDegreeOfDifficultyOfTheDiscipline("TC"));
        assertThat(3).isEqualTo(sistema.getDegreeOfDifficultyOfTheDiscipline("Calculo 2"));
        assertThat(2).isEqualTo(sistema.getDegreeOfDifficultyOfTheDiscipline("SI 1"));
		
		sistema.removeDisciplineOfPeriod("Prog 2", 2);
		
        assertThat(13).isEqualTo(sistema.getDegreeOfDifficulty(2));
	}
	
	/*
	 *  << USs DO PROJETO >>
	 */
	
	
	/*
	 * US: Nova maneira de usar o fluxograma
	 * Como aluno, desejo poder manipular as disciplinas, organizá-las e salvá-las da maneira 
	 * que preferir através de remoção e inserção das mesmas. O sistema inicia com a blocagem 
	 * padrão do curso e vai sendo moldado de acordo com minha alterações. Prérequisitos são 
	 * obedecidos quando disciplinas são adicionadas, mas ao movê-las, não. Quando movemos uma 
	 * disciplina, se ela não ficar antes de todos os seus pré-requisitos, ela deve ficar 
	 * vermelha na interface. O primeiro período pode ser editado livremente. Cada período tem
	 * um máximo de créditos, exceto o último. Disciplinas não podem ser removidas.
	 * 
	 * Devera ser testado 
	 *  - mover disciplina e a ao mover nao deve da erro, apenas retornar um boolean
	 * informando se a modificacao gerou ou nao problemas
	 *  - Primeiro periodo editavel
	 *  - Maximo de creditos em todos os periodos, menos no ultimo
	 */
	
	@Test
	public void testBaseMoverDisciplina(){
		//teste base
		assertThat(sistema.getTotalCredits(6)).isEqualTo(28);
		assertThat(sistema.getTotalCredits(8)).isEqualTo(18);
		try {
			assertThat(sistema.moveDisciplina("BD 2", 6, 8)).isEqualTo(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		assertThat(sistema.getTotalCredits(6)).isEqualTo(24);
		assertThat(sistema.getTotalCredits(8)).isEqualTo(22);
	}

	@Test
	public void testMoverDisciplina(){
		//teste envolvento prerequisito
		assertThat(sistema.getTotalCredits(1)).isEqualTo(24);
		try {
			assertThat(sistema.moveDisciplina("Calculo 1", 1, 8)).isEqualTo(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThat(sistema.getTotalCredits(1)).isEqualTo(20);
		assertThat(sistema.getTotalCredits(8)).isEqualTo(22);
		try {
			assertThat(sistema.moveDisciplina("Didatica 2", 1, 9)).isEqualTo(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaPrimeiroPeriodoEditavel(){
		assertThat(sistema.getTotalCredits(1)).isEqualTo(24);
		sistema.removeDisciplineOfPeriod("Prog 1", 1);
		assertThat(sistema.getTotalCredits(1)).isEqualTo(20);
		
		try {
			sistema.addForcedDisciplineInPeriod("Prog 1", 1);
		} catch (Exception e) {
		}

		assertThat(sistema.getTotalCredits(1)).isEqualTo(24);
	}
	
	@Test
	public void maxDeCreditosNosPeriodosExcetoNoUltimo(){
		try {
			sistema.addForcedDisciplineInPeriod("IC", 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i < 8; i++) {
			assertThat(sistema.getMaxCreditsOfPeriod(i)).isEqualTo(28);			
		}
		assertThat(sistema.getMaxCreditsOfPeriod(8)).isEqualTo(0);
	}
	
	@Test
	public void testaPersistencia(){
        assertThat(sistema.getTotalCredits(1)).isEqualTo(24);
		try {
			sistema.moveDisciplina("IC", 1, 8);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        assertThat(sistema.getTotalCredits(1)).isEqualTo(20);
        
		sistema.getTotalCredits(1);
		
		try {
			sistema = new Sistema();
			sistema.login("admin@email.com", "admin");
		} catch (Exception e) {
			e.printStackTrace();
		}

        assertThat(sistema.getTotalCredits(1)).isEqualTo(20);
	}
}
