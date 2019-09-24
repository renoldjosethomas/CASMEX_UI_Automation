package casmex.pages;

import org.openqa.selenium.By;

public class GeneralActions extends BaseAction {
	// Path of Page Objects
	protected String objUsername = "//label//input[@placeholder = 'Username']";
	protected String objPassword = "//label//input[@placeholder = 'Password']";
	protected String objLogin = "//input[@type='submit' and @value='Sign in']";
	protected String objLogout = "//a/span[text()='LOGOUT']";
	
	protected String objMenu = "//a[@class='bars']";
	protected String objTransaction = "//li/a/span[text() ='Transaction']";
	protected String objCash = "//li/a[text() ='Cash Receipt [F7]']";
	protected String objRemittance = "//li/a[text() ='Remittance [F5]']";

	//Get Remittance Page or Cash Receipt Page
	public void menuTransaction(String page) {
		System.out.println(parentDriver.toString());
		
		findElement(parentDriver, By.xpath(objMenu)).click();
		findElement(parentDriver, By.xpath(objTransaction)).click();
		if(page == "Remittance")
			findElement(parentDriver, By.xpath(objCash)).click();
		else if(page == "Cash Receipt")
		findElement(parentDriver, By.xpath(objRemittance)).click();
	}
	
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