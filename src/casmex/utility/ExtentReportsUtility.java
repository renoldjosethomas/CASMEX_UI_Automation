package casmex.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsUtility {

	private LogUtility log4j;
	private ExcelUtility excel;
	private ExtentReports extent;
	private ExtentTest extentLogger;
	private FailureScreenshotUtility capture;
	private ExtentHtmlReporter htmlReporter;

	public ExtentReportsUtility() {
		log4j = new LogUtility();
		excel = new ExcelUtility();
		capture = new FailureScreenshotUtility();
	}

	public void setExtentReportsConfig(String pathHTML, String pathXML) {
		extent = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(new File(pathHTML));
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setTheme(Theme.DARK);
	}

	public void logExtentTestDetails(int fromRow, int toRow) {
		// Log Test Details like Sprint, Author, etc.. in Extent Report
		try {
			for (int data = fromRow; data <= toRow; data++) {
				extent.setSystemInfo(excel.getCellData(data, 0), excel.getCellData(data, 1));
			}
		} catch (Exception sysEx) {
			log4j.error("logExtentTestDetails", sysEx);
		}
	}

	public void logExtentPass(String details) {
		// Log PASS in Extent Report with the Pass Message
		extentLogger.log(Status.PASS, details);
	}

	public void logExtentFail(WebDriver driverInst, Exception driverEx, String testName)
	// Log Screenshot in Extent Report using the Screenshot File path
	{
		try {
			log4j.error(testName, driverEx);

			String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driverInst;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String screenshot = "./" + testName + " " + dateTime;
			File destination = new File(screenshot);
			FileUtils.copyFile(source, destination);

			// extentLogger.log(Status.FAIL, info);
			extentLogger.fail("TEST FAILED" + driverEx.getMessage()
					+ extentLogger.addScreenCaptureFromPath(capture.getScreenshot(driverInst, testName)));
		} catch (Exception sysEx) {
			log4j.error("logExtentFail", sysEx);
		}
	}

	public void logExtentTestCase(String testName) {
		// Log PASS in Extent Report with the Pass Message
		extent.createTest(testName);
	}

	public void endExtentReports() {
		// flush() - to write or update test information to your report.
		extent.flush();
	}
}