package sistema;

import java.util.HashMap;
import java.util.Set;

import BD.BD;

public class User {
	
	/*
	 * Variables
	 */
	
	private BD 							db = new BD();
	private String 						email;
	private String 						password;
	private String 						name;
	private HashMap<String, String> 	plano = new HashMap<String, String>();
	
	/*
	 * Constructs
	 */
	
	public User(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
		loadPlano();
	}
	
	public User(String email, String password, String name, HashMap<String, String> plano) {
		this.email    = email;
		this.password = password;
		this.name     = name;
		this.plano    = plano;
	}
	
	public void saveUser() throws Exception {
		if(!db.addUser(email, password, name, getPlanoForSave())){
			throw new Exception("Usuario ja cadastrado anteriormente");
		}
	}
	
	private String getPlanoForSave() {
		String retorno = "";
		Set keys = plano.keySet();  
		int i =0;
		for (Object key : keys) {
			retorno+= key.toString()+" - "+plano.get(key);
			if(i++ < plano.size() - 1)
				retorno+=",";
		}
		return retorno;
	}

	/*
	 * Private methods
	 */
	
	private void loadPlano(){
		setPlano(db.getPlanoOfUser(email));
	}
	
	/*
	 * Getters and setters
	 */
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, String> getPlano() {
		return plano;
	}
	public void setPlano(HashMap<String, String> plano) {
		this.plano = plano;
	}
	public String getId(){
		return getEmail();
	}
	


}
