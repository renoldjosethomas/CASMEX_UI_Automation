package casmex.pages;

import org.openqa.selenium.By;

public class RemittanceActions extends BaseAction {

	// Path of Page Objects
	protected String objSearchDropDown = "//select[@name='ctl00$c$dSearchType']";
	protected String objSearchField = "//div/input[@name='ctl00$c$tSearch']";
	protected String objSearchButton = "//div/input[@type='image' and @name='ctl00$c$ibCodeSrch']";
	protected String objLast5Transactions = "//div/button[text()='Last 5 Transactions']";
	protected String objTransactionRefNum = "//table[@id='c_gvCShoTrn']//tr[";

	// Search Remittance by Customer Code / ID / Mobile / Name / Ref No / etc..
	public void searchRemittanceBy(String searchType, String searchInput) {
		parentSelect = select(parentDriver, findElement(parentDriver, By.xpath(objSearchDropDown)));
		parentSelect.selectByVisibleText(searchType);
		findElement(parentDriver, By.xpath(objSearchField)).sendKeys(searchInput);
		findElement(parentDriver, By.xpath(objSearchButton)).click();
	}

	public String getLast5Transactions(int trValue) {
		findElement(parentDriver, By.xpath(objLast5Transactions)).click();

		return findElement(parentDriver, By.xpath(objTransactionRefNum + trValue+1 + "]/td[2]")).getText();
	}

}
