package controllers;

import play.*;
import play.mvc.*;
import sistema.Login;
import sistema.Sistema;
import views.html.*;

public class Application extends Controller {
	
	private static Sistema sistema ;
	
	public static Result index() {
		try {
			sistema = new Sistema();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return meuPlano("","0");
    }
	
	public static Result meuPlano(String ID, String period){
		Auxiliar aux = new Auxiliar();
		try {
    		sistema.addForcedDisciplineInPeriod(ID.replace("_", " "), Integer.parseInt(period));
    		return ok(index.render(sistema, aux));
		} catch (Exception e) {
			return notFound(e.getMessage());
		}
	}
	
	public static Result login(String email, String password){
		Auxiliar aux = new Auxiliar();
		try {
			sistema.login(email, password);
	   		return ok(index.render(sistema, aux));
		} catch(Exception e){
			return notFound(":'( - "+e.getMessage());
		}
	}
	
	public static Result mover(String ID, String fromPeriod, String actualPeriod){
		Auxiliar aux = new Auxiliar();
		try {
			sistema.moveDisciplina(ID.replace("_", " "), Integer.parseInt(actualPeriod), Integer.parseInt(fromPeriod));
	   		return ok(index.render(sistema, aux));
		} catch (NumberFormatException e) {
			return notFound(":'(");
		} catch (Exception e) {
			return notFound(":(");
		}
	}
	
	public static Result start(){
		Auxiliar aux = new Auxiliar();
		return ok(login.render(sistema, aux));
	}
	

}
