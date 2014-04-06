package sistema;

import java.util.HashMap;

import BD.GerentArq;

public class Login {
	GerentArq db = new GerentArq();
	
	public User login(String email, String password){
		if(db.isUser(email, password))
			return db.getUser(email, password);
		return null;
	}
}
