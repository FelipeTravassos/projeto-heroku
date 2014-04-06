package sistema;

import java.util.List;

public class Sistema {
	User user = null;
	Plano plan = null;
	Login login = new Login();
	
	public void login(String email, String password) throws Exception{
		user = login.login(email, password);
		if(user == null){
			throw new Exception("Usuario nao cadastrado");
		}
		plan = user.getPlano();
	}

	public void resetPlan() {
		user.resetPlan();
		try {
			plan.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get total credits
	 * @return total credits for the current period
	 */
	public int getTotalCredits(int period) {
		return plan.getTotalCredits(period);
	}

	/**
	 * get Allocated Disciplines
	 * @return List of allocated disciplines
	 */
	public List<String> getAllocatedDisciplines(int period) {
		return plan.getAllocatedDisciplines(period);
	}

	/**
	 * Get name of discipline
	 * @param string: ID of discipline
	 * @return Name of discipline
	 */
	public String getNameDiscipline(String ID) {
		return plan.getNameDiscipline(ID);
	}

	/**
	 * Get total credits of discipline
	 * @param string: ID of discipline
	 * @return credits of discipline
	 */
	public int getCreditsDiscipline(String ID) {
		return plan.getCreditsDiscipline(ID);
	}

	/**
	 * Remove discipline of period
	 * @param ID: Id of the discipline
	 * @param period: period that will remove the discipline
	 */
	public void removeDisciplineOfPeriod(String ID, int period) {
		plan.removeDisciplineOfPeriod(ID, period);
	}

	/**
	 * get degree of difficulty for specific period
	 * @param period
	 * @return
	 */
	public int getDegreeOfDifficulty(int period) {
		return plan.getDegreeOfDifficulty(period);
	}

	/**
	 * get degree of difficulty of the discipline
	 * @param ID
	 * @return
	 */
	public int getDegreeOfDifficultyOfTheDiscipline(String ID) {
		return plan.getDegreeOfDifficultyOfTheDiscipline(ID);
	}

	/**
	 * Move the discipline
	 * @param ID of the discipline
	 * @param actualPeriod period actual of discipline
	 * @param fromPeriod period for where it goes discipline
	 * @return 
	 * @throws Exception 
	 */
	public boolean moveDisciplina(String ID, int actualPeriod, int fromPeriod) {
		try {
			return plan.moveDisciplina(ID, actualPeriod, fromPeriod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Add discipline in period, without verify prerequisites 
	 * @param ID: String with ID of discipline
	 * @param period: int period of discipline
	 * @return
	 */
	public void addForcedDisciplineInPeriod(String ID, int period) {
		plan.addForcedDisciplineInPeriod(ID, period);
	}

	/**
	 * get max credits of period
	 * @param i
	 * @return max credits of period
	 */
	public int getMaxCreditsOfPeriod(int period) {
		return plan.getMaxCreditsOfPeriod(period);
	}
}
