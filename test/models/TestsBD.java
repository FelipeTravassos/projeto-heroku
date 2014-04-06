package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import sistema.Disciplina;
import sistema.Login;
import BD.GerentArq;

public class TestsBD {

	GerentArq bd = new GerentArq();
	
	
	
	@Test
	public void testNewUser() {
		String email = "felipe.travassos@ccc.ufcg.edu.br";
		String password = "1234";
		String nome = "Felipe";
		
		bd.addUser(email, password, nome);
		
		List<Disciplina> disciplinesOfUser = bd.getDisciplinesOfPlan(email);
        assertThat(disciplinesOfUser.size()).isEqualTo(55);
	}
	
	@Test
	public void testLogin(){
		assertThat((new Login()).login("blabla", ":p")).isEqualTo(null);
		assertThat((new Login()).login("felipe.travassos@ccc.ufcg.edu.br", "1234").getName()).isEqualTo("Felipe");
	}

}
