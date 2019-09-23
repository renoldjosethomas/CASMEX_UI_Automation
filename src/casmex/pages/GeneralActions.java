package casmex.pages;

import org.openqa.selenium.By;

public class GeneralActions extends BaseAction {
	// Path of Page Objects
	protected String objUsername = "//label//input[@placeholder = 'Username']";
	protected String objPassword = "//label//input[@placeholder = 'Password']";
	protected String objLogin = "//input[@type='submit' and @value='Sign in']";
	protected String objLogout = "//li[10]/a/span[text()='Logout']";

	public void closeBrowser() {
		parentDriver.close();
	}

	public void performLogin(String url, String username, String password) {
		parentDriver.get(url);
		findElement(parentDriver, By.xpath(objUsername)).sendKeys(username);
		findElement(parentDriver, By.xpath(objPassword)).sendKeys(password);
		findElement(parentDriver, By.xpath(objLogin)).click();
	}

	public void performLogout() {
		findElement(parentDriver, By.xpath(objLogout)).click();
	}
}