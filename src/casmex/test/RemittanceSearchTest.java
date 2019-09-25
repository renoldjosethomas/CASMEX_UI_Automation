package casmex.test;

import java.util.Dictionary;

import org.testng.Assert;

import casmex.pages.GeneralActions;
import casmex.pages.RemittanceActions;

public class RemittanceSearchTest extends BaseTest {

	private GeneralActions general;
	private RemittanceActions remittance;

	public RemittanceSearchTest() {
		general = new GeneralActions();
		remittance = new RemittanceActions();
	}

	public boolean searchCustomerCode(Dictionary<String, String> testData) {
		try {

			// Arrange
			general.menuTransaction("Remittance");
			remittance.searchRemittanceBy(testData.get("Search Type"), testData.get("Customer Code"));

			// Assert
			Assert.assertEquals(remittance.getLast5Transactions(1),testData.get("Expected Result"));

			return true;

		} catch (Exception driverEx) {
			exceptionMessage = driverEx.getMessage();
			return false;
		}
	}

	public boolean searchMobile(Dictionary<String, String> testData) {
		try {

			// Arrange
			general.menuTransaction("Remittance");
			remittance.searchRemittanceBy(testData.get("Search Type"), testData.get("Mobile"));

			// Assert
			Assert.assertEquals(remittance.getBeneficiary("Name"), testData.get("Expected Result"));

			return true;

		} catch (Exception driverEx) {
			exceptionMessage = driverEx.getMessage();
			return false;
		}
	}

}
