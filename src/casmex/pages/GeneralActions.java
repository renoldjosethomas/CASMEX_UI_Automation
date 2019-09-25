package casmex.pages;

import org.openqa.selenium.By;

import casmex.utility.ExtentReportsUtility;

public class GeneralActions extends BaseAction {

	// Path of Page Objects
	private String objUsername = "//label//input[@placeholder = 'Username']";
	private String objPassword = "//label//input[@placeholder = 'Password']";
	private String objLogin = "//input[@type='submit' and @value='Sign in']";
	private String objLogout = "//a/span[text()='LOGOUT']";
	
	private String objHomePage = "//span[@id='lblExchangeHeader']";
	private String objMenu = "//a[@class='bars']";
	private String objTransaction = "//li/a/span[text() ='Transaction']";
	private String objCash = "//li/a[text() ='Cash Receipt [F7]']";
	private String objRemittance = "//li/a[text() ='Remittance [F5]']";
	
	private ExtentReportsUtility report;
	
	public GeneralActions() {
		report = new ExtentReportsUtility();
	}

	//Get Remittance Page or Cash Receipt Page
	public void menuTransaction(String page) {
		if(page == "Remittance") {
			parentDriver.get("http://182.72.164.246:91/oiexc/CASMEX/transaction/Remittance.aspx?mode=R");
			report.nodeTestPass("Navigate to Remittance Page");
		} else if(page == "Cash Receipt") {
			parentDriver.get("http://182.72.164.246:91/OIEXC/CASMEX/transaction/cashreceive.aspx");
			report.nodeTestPass("Navigate to Remittance Page");
		}
	}
	
	public void homePage() {
		findElement(parentDriver, By.xpath(objHomePage)).click();
	}

	
	public void closeBrowser() {
		parentDriver.close();
	}

	public void performLogin(String url, String username, String password) throws InterruptedException {
		parentDriver.get(url);
		findElement(parentDriver, By.xpath(objUsername)).sendKeys(username);
		findElement(parentDriver, By.xpath(objPassword)).sendKeys(password);
		findElement(parentDriver, By.xpath(objLogin)).click();
		Thread.sleep(3000);
	}

	public void performLogout() {
		findElement(parentDriver, By.xpath(objLogout)).click();
	}
}