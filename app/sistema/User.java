package sistema;

import BD.GerentArq;

public class User {
	
	/*
	 * Variables
	 */
	private GerentArq 	db 			= new GerentArq();
	private String 		email;
	private String 		password;
	private String 		name;
	private Plano 		plano;

	public User(String email, String password, String name) throws Exception {
		this.plano = new Plano(email);
		this.email = email;
		this.password = password;
		this.name = name;
	}

	/**
	 * Add new user in system
	 * @throws Exception: Error if the user already exists
	 */
	public void saveUser() throws Exception {
		if(!db.addUser(email, password, name)){
			throw new Exception("Usuario ja cadastrado anteriormente");
		}
	}

	/**
	 * Update plan of user
	 * @param disciplineID: String with ID of discipline for update
	 * @param fromPeriod: int with periods where does the discipline
	 */
	public void updatePlan(String disciplineID, int fromPeriod){
		db.updatePlanoUser(this.email, disciplineID, fromPeriod);
	}

	/**
	 * @return String with email of user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set email
	 * @param email: String
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return String with password of user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set password
	 * @param password: String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return String with name of user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of user
	 * @param name:String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String with plan of user
	 */
	public Plano getPlano() {
		return plano;
	}
	
	/**
	 * Set plan of user
	 * @param plano: Plano
	 */
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	
	/**
	 * @return String with ID of user
	 */
	public String getId(){
		return getEmail();
	}

	/**
	 * Reset user plan to be equal to default plan
	 */
	public void resetPlan() {
		db.reset(getId());
	}
}