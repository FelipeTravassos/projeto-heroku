package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import BD.GerentArq;
import sistema.User;

public class TestsBD {

	GerentArq bd = new GerentArq();
	
	@Test
	public void testNewUser() {
		String email = "felipe.travassos@ccc.ufcg.edu.br";
		String password = "1234";
		String nome = "Felipe";
		
		bd.addUser(email, password, nome, "disc A - 1");
		
		HashMap<String, String> disciplinesOfUser = bd.getDisciplinasOfUser(email, password);
        assertThat(disciplinesOfUser.size()).isEqualTo(1);
        assertThat(disciplinesOfUser.get("disc A")).isEqualTo("1");
	}

}
