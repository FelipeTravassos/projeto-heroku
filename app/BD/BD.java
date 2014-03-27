package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sistema.Disciplina;

public class BD {
	
	public BD() {
		criarTabelas();
		uploadBD();
	}
	
	private void criarTabelas(){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:disciplinas.db");
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS DISCIPLINAS " +
	                   "(ID TEXT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " CREDITOS       INT     NOT NULL, " + 
	                   " DIFICULDADE    INT     NOT NULL, " + 
	                   " PREREQUISITOS  TEXT    NOT NULL, " + 
	                   " PERIODO        INT     NOT NULL)"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE IF NOT EXISTS USERS " +
                  "(EMAIL TEXT PRIMARY KEY     NOT NULL," +
                  " PASSWORD           TEXT    NOT NULL, " +
                  " NAME           	   TEXT    NOT NULL, " + 
                  " PLANO        	   TEXT    NOT NULL)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	    }
	}
	
	
	
	public boolean addUser(String email, String password, String name, String plano){
		boolean retorno = verifyExistentUser(email);
		
		if(retorno){
			Connection c = null;
		    Statement stmt = null;
		    try {
		    	Class.forName("org.sqlite.JDBC");
			    c = DriverManager.getConnection("jdbc:sqlite:disciplinas.db");
			    stmt = c.createStatement();
			    stmt.executeUpdate("");
			    		  
			    String sql = "INSERT INTO USERS (EMAIL,PASSWORD,NAME,PLANO) " +
                 		"VALUES ('"+email+"', '"+password+"', "+name+", "+plano+" );"; 
			    stmt.executeUpdate(sql);
			   
			    stmt.close();
			    c.commit();
			    c.close();	     
		    } catch ( Exception e ) {
		    }
		}
		
		return retorno;
	}

	private boolean verifyExistentUser(String email) {
		boolean retorno = true;
		List<User> users = getUsers();
		for (User user : users) {
			if(user.getId().equals(email)){
				retorno = false;
				break;
			}
		}
		
				
		return retorno;
	}
	
	
	
		
	public void updatePlanoModificado(List<Disciplina> plano){
		Connection c = null;
	    Statement stmt = null;
	    try {
	    	Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:disciplinas.db");
		      stmt = c.createStatement();
		      stmt.executeUpdate("");
		      
		      String sql = "CREATE TABLE IF NOT EXISTS PLANO_MODIFICADO " +
	                  "(ID TEXT PRIMARY KEY     NOT NULL," +
	                  " NAME           TEXT    NOT NULL, " + 
	                  " CREDITOS       INT     NOT NULL, " + 
	                  " DIFICULDADE    INT     NOT NULL, " + 
	                  " PREREQUISITOS  TEXT    NOT NULL, " + 
	                  " PERIODO        INT     NOT NULL)"; 
		      stmt.executeUpdate(sql);
	   
		      for (int i = 0; i < plano.size(); i++) {
		    	  sql = "INSERT INTO PLANO_MODIFICADO (ID,NAME,CREDITOS,DIFICULDADE,PREREQUISITOS,PERIODO) " +
	                   		"VALUES ('"+plano.get(i).getName()+"', '"+plano.get(i).getName()+"', "+plano.get(i).getCredits()+", "+
	                   		plano.get(i).getDifficulty()+", '"+strDiscilpinas(plano.get(i).getPrerequisites())+"', "+
	                   		plano.get(i).getPeriod()+" );"; 
		    	  stmt.executeUpdate(sql);
		      }
		      stmt.close();
		      c.commit();
		      c.close();	     
	    } catch ( Exception e ) {
	    }
	}
	
	public void uploadBD(){
		xmlParser xml = new xmlParser();
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:disciplinas.db");
		      c.setAutoCommit(false);
		      List<Disciplina> allDisciplines = xml.getListOfDiscipline();
		      stmt = c.createStatement();
		      for (int i = 0; i < allDisciplines.size(); i++) {
		    	  String sql = "INSERT INTO DISCIPLINAS (ID,NAME,CREDITOS,DIFICULDADE,PREREQUISITOS,PERIODO) " +
		                   		"VALUES ('"+allDisciplines.get(i).getName()+"', '"+allDisciplines.get(i).getName()+"', "+allDisciplines.get(i).getCredits()+", "+
		                   		allDisciplines.get(i).getDifficulty()+", '"+strDiscilpinas(allDisciplines.get(i).getPrerequisites())+"', "+
		                   		allDisciplines.get(i).getPeriod()+" );"; 
		    	  stmt.executeUpdate(sql);
		    	  sql = "INSERT INTO PLANO_MODIFICADO (ID,NAME,CREDITOS,DIFICULDADE,PREREQUISITOS,PERIODO) " +
	                   		"VALUES ('"+allDisciplines.get(i).getName()+"', '"+allDisciplines.get(i).getName()+"', "+allDisciplines.get(i).getCredits()+", "+
	                   		allDisciplines.get(i).getDifficulty()+", '"+strDiscilpinas(allDisciplines.get(i).getPrerequisites())+"', "+
	                   		allDisciplines.get(i).getPeriod()+" );"; 
		    	  stmt.executeUpdate(sql);
		      }
		      stmt.close();
		      c.commit();
		      c.close();
		    } catch ( Exception e ) {
		    }
	}
	
	private String strDiscilpinas(String[] array){
		String retorno = "";
		for (int i = 0; i < array.length; i++) {
			retorno += array[i];
			if(i < array.length - 1)
				retorno += " - ";
		}
		return retorno;
	}
	
	public List<Disciplina> getDisciplinas(){
		List<Disciplina> retorno = new ArrayList<Disciplina>();
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:disciplinas.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM PLANO_MODIFICADO;" );
	      while ( rs.next() ) {
	         String  name = rs.getString("name");
	         int creditos  = rs.getInt("creditos");
	         int  dificuldade = rs.getInt("dificuldade");
	         String prerequisitos = rs.getString("prerequisitos");
	         int period = rs.getInt("periodo");
	         Disciplina disciplina = new Disciplina(name, creditos, dificuldade, prerequisitos.split(" - "));
	         disciplina.setPeriod(period);
	         retorno.add(disciplina);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	    }
	    return retorno;
	}
}
