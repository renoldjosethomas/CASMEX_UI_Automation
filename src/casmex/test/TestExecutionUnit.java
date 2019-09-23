package casmex.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import casmex.pages.BaseAction;
import casmex.utility.ExcelUtility;
import casmex.utility.ExtentReportsUtility;
import casmex.utility.LogUtility;

public class TestExecutionUnit extends BaseTest {
	private static Properties configFile;
	private LogUtility log4j;

	public TestExecutionUnit() {
		loadConfigFile(configFile = new Properties());
		log4j = new LogUtility();
	}

	public static void main(String args[]) throws Exception {
		// Initializing Instance of the following classes to use their methods
		ExtentReportsUtility report = new ExtentReportsUtility();
		LogUtility log = new LogUtility();
		BaseTest testBase = new BaseTest();
		BaseAction actBase = new BaseAction();
		ExcelUtility excel = new ExcelUtility();
		TestExecutionUnit txu = new TestExecutionUnit();

		// Configure Selenium WebDriver
		BaseAction.parentDriver = actBase.initDriverInstance(BaseAction.parentDriver, 
														configFile.getProperty("driverKey"),
														configFile.getProperty("driverPath"));
														actBase.initDriverActions(BaseAction.parentDriver);

		// Configure Excel
		excel.setExcelConfig(configFile.getProperty("excelFilePath"), 
							configFile.getProperty("excelSheetName"));

		// Configure Extent Reports
		report.setExtentReportsConfig(configFile.getProperty("reportsFilePath"),
									configFile.getProperty("reportsConfigFile"));

		// Log Test Details in log4j file and Extent Report
		log.logTestDetails(7, 13);
		report.logExtentTestDetails(7, 13);

		// Iterate through test cases and execute them step by step
		for (int rowNum = 16; rowNum <= ExcelUtility.lastRowNumber; rowNum++) {

			// Retrieve Test Details - Page / Test Name / Test Case Description
			ArrayList<String> testDetails = excel.getRowData(rowNum, 0, 3);

			// Retrieve Test Data Parameters
			ArrayList<String> testData = excel.getRowData(rowNum, 3);
			// Login
			testBase.login(configFile.getProperty("url"),
					configFile.getProperty("username"),
					configFile.getProperty("password"));
			// Log test case in the report (Test Case Name)
			report.logExtentTestCase(testDetails.get(2));
			// Execute Test Case
			txu.executeTestCase(testDetails, testData);
			//Logout
			testBase.logout();
			// Wait for Success Toasters to Dissolve
			Thread.sleep(2000);
			// Log test case information in log4j file
			log.info(testDetails.get(3));
		}
		report.endExtentReports();
		// mail.sendEmailReport();
		System.out.println("--------- UI AUTOMATION SUITE SUCCESSFULLY RUN ---------");
	}

	private static void loadConfigFile(Properties configFile) {
		try {
			InputStream file = new FileInputStream("./src/config.properties");
			configFile.load(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean executeTestCase(ArrayList<String> testDetails, ArrayList<String> testData)
	// Invoke methods using action_keyword in Test Case
	{
		try {
			boolean isSuccess = false;
			Class<?> classRef = Class.forName("csPageObjects." + testDetails.get(0));
			// Dynamically create instances of classes
			Object classInst = (Object) classRef.newInstance();
			Method method[] = classInst.getClass().getMethods();
			for (int i = 0; i < method.length; i++) {
				if (method[i].getName().equalsIgnoreCase(testDetails.get(1))) {
					if (testData.isEmpty()) {
						// invoking page object methods without arguments
						System.out.println("Test Data Empty");
						isSuccess = (boolean) method[i].invoke(classInst);
					} else {
						// invoking page object methods with arguments
						isSuccess = (boolean) method[i].invoke(classInst, testData);

					}
				}
			}
			return isSuccess;
		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException sysEx) {
			log4j.error("executeTestCase", sysEx);
			return false;
		}
	}
}