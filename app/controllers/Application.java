package controllers;

import java.util.ArrayList;
import java.util.List;

import play.*;
import play.mvc.*;
import sistema.Plano;
import views.html.*;

public class Application extends Controller {

	private static Plano plano ;
	
	public static Result index() {
		try {
			plano = new Plano();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return meuPlano("","0");
    }
	
	public static Result meuPlano(String ID, String period){
		try {
    		Auxiliar aux = new Auxiliar();
			plano.addDisciplineInPeriod(ID.replace("_", " "), Integer.parseInt(period));
    		return ok(index.render(plano, aux));
		} catch (Exception e) {
			return notFound(e.getMessage());
		}
	}
	
	public static Result remove(String ID, String period){
   		Auxiliar aux = new Auxiliar();
		plano.removeDisciplineOfPeriod(ID.replace("_", " "), Integer.parseInt(period));
   		return ok(index.render(plano, aux));
	}
	

}
