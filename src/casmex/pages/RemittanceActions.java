package casmex.pages;

import org.openqa.selenium.By;

import casmex.utility.ExtentReportsUtility;

public class RemittanceActions extends BaseAction {

	private ExtentReportsUtility report;

	public RemittanceActions() {
		report = new ExtentReportsUtility();
	}

	// Path of Page Objects
	private String objSearchDropDown = "//select[@name='ctl00$c$dSearchType']";
	private String objSearchField = "//div/input[@name='ctl00$c$tSearch']";
	private String objSearchButton = "//div/input[@type='image' and @name='ctl00$c$ibCodeSrch']";

	private String objLast5Transactions = "//div/button[text()='Last 5 Transactions']";
	private String objCloseLast5TransButton = "//button[@class='ui-dialog-titlebar-close']";
	private String objTransactionRefNum = "//table[@id='c_gvCShoTrn']//tr[";

	private String objBenfName = "//table[@id='c_gSP']//tr[2]/td[3]";
	private String objBenfAcc = "//table[@id='c_gSP']//tr[2]/td[4]";
	private String objBenfBank = "//table[@id='c_gSP']//tr[2]/td[5]";

	// Search Remittance by Customer Code / ID / Mobile / Name / Ref No / etc..
	public void searchRemittanceBy(String searchType, String searchInput) {
		parentSelect = select(parentDriver, By.xpath(objSearchDropDown));
		parentSelect.selectByVisibleText(searchType);

		report.nodeTestPass("Select search category for remittance");
		findElement(parentDriver, By.xpath(objSearchField)).sendKeys(searchInput);
		report.nodeTestPass("Enter search category");
		findElement(parentDriver, By.xpath(objSearchButton)).click();
		report.nodeTestPass("Search " + searchType + " : " + searchInput);
	}

	public String getLast5Transactions(int trValue) {
		findElement(parentDriver, By.xpath(objLast5Transactions)).click();
		report.nodeTestPass("Open last 5 transactions of the customer");
		String transaction = findElement(parentDriver, By.xpath(objTransactionRefNum + (trValue + 1) + "]/td[2]")).getText();
		findElement(parentDriver, By.xpath("//div[@aria-labelledby='ui-id-110']")).click();
		findElement(parentDriver, By.xpath(objCloseLast5TransButton)).click();
		report.nodeTestPass("Close - Last 5 transactions modal");

		return transaction;
	}

	public String getBeneficiary(String detail) {
		report.nodeTestPass("Get beneficiary details");
		switch (detail) {
		case ("Name"):
			return findElement(parentDriver, By.xpath(objBenfName)).getText();
		case ("Account"):
			return findElement(parentDriver, By.xpath(objBenfAcc)).getText();
		default:
			return findElement(parentDriver, By.xpath(objBenfBank)).getText();
		}

	}

}
