package casmex.test;

import casmex.pages.GeneralActions;

public class BaseTest {

	private GeneralActions general;

	protected String actionPage, actionKeyword, actionDescription;

	public BaseTest() {
		general = new GeneralActions();
		System.out.println("Base Test Initalized");
	}

	public boolean login(String url, String username, String password) {
		try {
			general.performLogin(url, username, password);
			return true;

		} catch (Exception driverEx) {
			return false;
		}
	}

	public boolean logout() {
		try {
			general.performLogout();
			return true;

		} catch (Exception driverEx) {
			return false;
		}
	}

}
