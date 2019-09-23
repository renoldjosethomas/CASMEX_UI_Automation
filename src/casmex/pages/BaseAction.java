package casmex.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import casmex.utility.LogUtility;

public class BaseAction {

	public static WebDriver parentDriver;
	protected static Actions parentAction;
	protected static Select parentSelect;
	protected static WebDriverWait parentWait;
	protected static FluentWait<WebDriver> parentFluentWait;
	protected static ArrayList<String> browserTabs;

	public static WebDriver childDriver;
	protected static Actions childAction;
	protected static Select childSelect;
	protected static WebDriverWait childWait;
	protected static FluentWait<WebDriver> childFluentWait;

	public String actionDescription;

	protected LogUtility log4j;

	public BaseAction() {
		log4j = new LogUtility();
		System.out.println("Base Action Initalized");
	}

	public WebDriver initDriverInstance(WebDriver driver, String key, String path) {
		// Initialize browser.
		System.setProperty(key, path);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	public WebElement findElement(WebDriver driver, By by) {
		WebElement element = null;
		try {
			if (driver == parentDriver) {
				element = parentDriver.findElement(by);
				parentWait.until(ExpectedConditions.visibilityOf(element));
				parentFluentWait.until(ExpectedConditions.elementToBeClickable(element));
			} else if (driver == childDriver) {
				element = childDriver.findElement(by);
				childWait.until(ExpectedConditions.visibilityOf(element));
				childFluentWait.until(ExpectedConditions.elementToBeClickable(element));
			}
			return element;
		} catch (Exception sysEx) {
			log4j.error("findElement", sysEx);
			return element;
		}
	}

	public WebElement mouseHover(WebDriver driver, WebElement element) {
		try {
			if (driver == parentDriver) {
				parentAction.moveToElement(element);
			} else if (driver == childDriver) {
				childAction.moveToElement(element);
			}
			return element;
		} catch (Exception sysEx) {
			log4j.error("mouseHover", sysEx);
			return element;
		}
	}

	public void initDriverActions(WebDriver driver) {
		if (driver == parentDriver) {
			parentAction = new Actions(driver);
			parentWait = new WebDriverWait(driver, 30);
			parentFluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		} else if (driver == childDriver) {
			childAction = new Actions(driver);
			childWait = new WebDriverWait(driver, 30);
			childFluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		}
	}

	public void openNewTab() {
		int tabs = 0;
		((JavascriptExecutor) parentDriver).executeScript("window.open()");
		browserTabs = new ArrayList<String>(parentDriver.getWindowHandles());
		tabs = browserTabs.size() - 1;
		// Pass Driver Control to latest opened tab
		parentDriver.switchTo().window(browserTabs.get(tabs));
	}
}
